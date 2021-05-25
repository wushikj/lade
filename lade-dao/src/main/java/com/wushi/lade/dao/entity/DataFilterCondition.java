package com.wushi.lade.dao.entity;

import net.sf.jsqlparser.expression.Expression;

/**
 * @author wushi
 * @date 2021/3/11 16:06
 */
public class DataFilterCondition {
    private boolean tableFilter;
    private String column;
    private Expression expression;

    public boolean isTableFilter() {
        return tableFilter;
    }

    public void setTableFilter(boolean tableFilter) {
        this.tableFilter = tableFilter;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
