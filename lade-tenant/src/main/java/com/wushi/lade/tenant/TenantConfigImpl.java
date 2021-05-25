package com.wushi.lade.tenant;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.wushi.lade.dao.interfaces.TenantConfig;
import com.wushi.lade.tenant.properties.TenantProperties;
import com.wushi.lade.tenant.resolver.ITenantResolver;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wushi
 * @date 2020/1/13 15:54
 * @description
 */
public class TenantConfigImpl implements TenantConfig {

    private TenantProperties tenantProperties;

    private ITenantResolver tenantResolver;

    public TenantProperties getTenantProperties() {
        return tenantProperties;
    }

    public void setTenantProperties(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    public ITenantResolver getTenantResolver() {
        return tenantResolver;
    }

    public void setTenantResolver(ITenantResolver tenantResolver) {
        this.tenantResolver = tenantResolver;
    }


    @Override
    public void set(PaginationInterceptor paginationInterceptor) {
        //如果没有启动多租户直接返回null
        if (!tenantProperties.isEnabled()) {
            return;
        }
        List<ISqlParser> sqlParserList = paginationInterceptor.getSqlParserList();
        if (sqlParserList == null) {
            sqlParserList = new ArrayList<>();
        }
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                String tenantId = tenantResolver.getTenantId();
                return new StringValue(tenantId);
            }

            @Override
            public String getTenantIdColumn() {
                return tenantProperties.getFieldName();
            }

            @Override
            public boolean doTableFilter(String tableName) {
                //指定的表不需要添加租户过滤
                return tenantProperties.getExcludeTable() != null && tenantProperties.getExcludeTable().contains(tableName);
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
    }
}
