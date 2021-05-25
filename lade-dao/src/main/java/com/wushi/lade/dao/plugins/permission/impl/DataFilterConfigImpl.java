package com.wushi.lade.dao.plugins.permission.impl;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.wushi.lade.dao.entity.DataFilterCondition;
import com.wushi.lade.dao.plugins.permission.DataFilterConfig;
import com.wushi.lade.dao.plugins.permission.DataFilterHandler;
import com.wushi.lade.dao.plugins.permission.DataFilterResolver;
import com.wushi.lade.dao.plugins.permission.DataFilterSqlParser;
import com.wushi.lade.dao.properties.DataFilterProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wushi
 * @date 2020/9/28 14:53
 */
@Service
public class DataFilterConfigImpl implements DataFilterConfig {

    private DataFilterProperties dataFilterProperties;

    @Autowired
    public void setDataFilterProperties(DataFilterProperties dataFilterProperties) {
        this.dataFilterProperties = dataFilterProperties;
    }

    @Autowired(required = false)
    private DataFilterResolver dataFilterResolver;

    @Override
    public void set(PaginationInterceptor paginationInterceptor) {
        if (this.dataFilterProperties == null || this.dataFilterProperties.getEnabled() == null || !this.dataFilterProperties.getEnabled()) {
            return;
        }
        List<ISqlParser> sqlParserList = paginationInterceptor.getSqlParserList();
        if (sqlParserList == null) {
            sqlParserList = new ArrayList<>();
        }
        DataFilterSqlParser dataFilterSqlParser = new DataFilterSqlParser(new DataFilterHandler() {

            ThreadLocal<DataFilterCondition> conditionHolder = new ThreadLocal<>();

            @Override
            public Expression getDataFilter(boolean where) {
                return conditionHolder.get() != null ? conditionHolder.get().getExpression() : null;
            }

            @Override
            public String getDataFilterColumn() {
                return conditionHolder.get() != null ? conditionHolder.get().getColumn() : null;
            }

            @Override
            public boolean doTableFilter(Table table, List<SelectItem> selectItems) {
                if (dataFilterResolver != null) {
                    DataFilterCondition dataFilterCondition = dataFilterResolver.doTableFilter(table, selectItems);
                    conditionHolder.set(dataFilterCondition);
                    return dataFilterCondition.isTableFilter();
                }
                return true;
            }
        });
        sqlParserList.add(dataFilterSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
    }
}
