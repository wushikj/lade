package com.wushi.lade.dao.plugins.permission;

import com.baomidou.mybatisplus.core.parser.AbstractJsqlParser;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;

import java.util.List;

/**
 * @author wushi
 * @date 2020/9/28 11:18
 */
public class DataFilterSqlParser extends AbstractJsqlParser {

    private DataFilterHandler dataFilterHandler;

    @Override
    public void processInsert(Insert insert) {
        return;
    }

    @Override
    public void processDelete(Delete delete) {
        return;
    }

    @Override
    public void processUpdate(Update update) {
        return;
    }

    @Override
    public void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            this.processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                this.processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                operationList.getSelects().forEach(this::processSelectBody);
            }
        }
    }

    public DataFilterSqlParser(DataFilterHandler dataFilterHandler) {
        this.dataFilterHandler = dataFilterHandler;
    }

    public DataFilterHandler getDataFilterHandler() {
        return dataFilterHandler;
    }

    protected void processPlainSelect(PlainSelect plainSelect) {
        this.processPlainSelect(plainSelect, false);
    }

    protected void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            if (!getDataFilterHandler().doTableFilter(fromTable, plainSelect.getSelectItems())) {
                plainSelect.setWhere(this.builderExpression(plainSelect.getWhere(), fromTable));
                if (addColumn) {
                    plainSelect.getSelectItems().add(new SelectExpressionItem(new Column(dataFilterHandler.getDataFilterColumn())));
                }
            }
        } else {
            this.processFromItem(fromItem);
        }

        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach((j) -> {
                this.processJoin(j);
                this.processFromItem(j.getRightItem());
            });
        }

    }

    protected void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null) {
                subJoin.getJoinList().forEach(this::processJoin);
            }

            if (subJoin.getLeft() != null) {
                this.processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                this.processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            this.logger.debug("Perform a subquery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    this.processSelectBody(subSelect.getSelectBody());
                }
            }
        }

    }

    protected void processJoin(Join join) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table) join.getRightItem();
            if (false) {
                return;
            }

            join.setOnExpression(this.builderExpression(join.getOnExpression(), fromTable));
        }

    }

    protected Expression builderExpression(Expression currentExpression, Table table) {
        Expression dataRangeExpression = this.dataFilterHandler.getDataFilter(false);
        Expression appendExpression = dataRangeExpression;
//        if (dataRangeExpression != null) {
//            if (!(dataRangeExpression instanceof SupportsOldOracleJoinSyntax)) {
//                appendExpression = new EqualsTo();
//                ((EqualsTo) appendExpression).setLeftExpression(this.getAliasColumn(table));
//                ((EqualsTo) appendExpression).setRightExpression(dataRangeExpression);
//            } else {
//                appendExpression = this.processTableAlias4CustomizedTenantIdExpression(dataRangeExpression, table);
//            }
//        }
        if (currentExpression == null) {
            return appendExpression;
        } else {
            if (currentExpression instanceof BinaryExpression) {
                BinaryExpression binaryExpression = (BinaryExpression) currentExpression;
                this.doExpression(binaryExpression.getLeftExpression());
                this.doExpression(binaryExpression.getRightExpression());
            } else if (currentExpression instanceof InExpression) {
                InExpression inExp = (InExpression) currentExpression;
                ItemsList rightItems = inExp.getRightItemsList();
                if (rightItems instanceof SubSelect) {
                    this.processSelectBody(((SubSelect) rightItems).getSelectBody());
                }
            }
            if (appendExpression == null) {
                return currentExpression;
            }

            return currentExpression instanceof OrExpression ? new AndExpression(new Parenthesis(currentExpression), (Expression) appendExpression) : new AndExpression(currentExpression, (Expression) appendExpression);
        }
    }

    protected void doExpression(Expression expression) {
        if (expression instanceof FromItem) {
            this.processFromItem((FromItem) expression);
        } else if (expression instanceof InExpression) {
            InExpression inExp = (InExpression) expression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                this.processSelectBody(((SubSelect) rightItems).getSelectBody());
            }
        }

    }

    protected Expression processTableAlias4CustomizedTenantIdExpression(Expression expression, Table table) {
        return expression;
    }

    protected Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (null == table.getAlias()) {
            column.append(table.getName());
        } else {
            column.append(table.getAlias().getName());
        }

        column.append(".");
        column.append(this.dataFilterHandler.getDataFilterColumn());
        return new Column(column.toString());
    }
}
