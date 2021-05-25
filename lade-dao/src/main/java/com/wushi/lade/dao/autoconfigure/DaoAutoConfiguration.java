package com.wushi.lade.dao.autoconfigure;

import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.wushi.lade.dao.interfaces.TenantConfig;
import com.wushi.lade.dao.plugins.permission.DataFilterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wushi
 * @date 2020/1/20 15:03
 * @description
 */
@Configuration
public class DaoAutoConfiguration {

    /**
     * 多租户配置信息
     **/
    private TenantConfig tenantConfig;

    private DataFilterConfig dataFilterConfig;

    public DaoAutoConfiguration(@Autowired(required = false) TenantConfig tenantConfig, @Autowired(required = false) DataFilterConfig dataFilterConfig) {
        this.tenantConfig = tenantConfig;
        this.dataFilterConfig = dataFilterConfig;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        if (this.tenantConfig != null) {
            this.tenantConfig.set(paginationInterceptor);
        }
        if (this.dataFilterConfig != null) {
            this.dataFilterConfig.set(paginationInterceptor);
        }
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * Sequence主键自增
     *
     * @return 返回oracle自增类
     * @author wushi
     * @date 2020/1/10
     */
    @Bean
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }
}
