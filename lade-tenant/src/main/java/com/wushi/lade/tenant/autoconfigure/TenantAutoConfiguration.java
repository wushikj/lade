package com.wushi.lade.tenant.autoconfigure;

import com.wushi.lade.dao.interfaces.TenantConfig;
import com.wushi.lade.tenant.TenantConfigImpl;
import com.wushi.lade.tenant.properties.TenantProperties;
import com.wushi.lade.tenant.resolver.DominTenantResolver;
import com.wushi.lade.tenant.resolver.ITenantResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wushi
 * @date 2020/1/14 9:58
 * @description
 */
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
@ConditionalOnClass(TenantConfigImpl.class)
@ConditionalOnProperty(value = "wushi.tenant.enabled", havingValue = "true")
public class TenantAutoConfiguration {

    private TenantProperties tenantProperties;

    private ITenantResolver tenantResolver;

    public TenantProperties getTenantProperties() {
        return tenantProperties;
    }

    @Autowired
    public void setTenantProperties(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    public ITenantResolver getTenantResolver() {
        return this.tenantResolver;
    }

    @Autowired(required = false)
    public void setTenantResolver(ITenantResolver tenantResolver) {
        this.tenantResolver = tenantResolver;
    }

    @Bean
    @ConditionalOnMissingBean
    public TenantConfig tenantConfig() {
        TenantConfigImpl tenantConfigImpl = new TenantConfigImpl();
        tenantConfigImpl.setTenantProperties(this.tenantProperties);
        tenantConfigImpl.setTenantResolver(this.tenantResolver);
        return tenantConfigImpl;
    }

    @Bean
    @ConditionalOnMissingBean
    public ITenantResolver tenantResolver() {
        if (this.getTenantResolver() == null) {
            return new DominTenantResolver();
        } else {
            return this.tenantResolver;
        }
    }
}


