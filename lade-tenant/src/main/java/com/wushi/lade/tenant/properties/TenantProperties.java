package com.wushi.lade.tenant.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wushi
 * @date 2020/1/13 15:56
 * @description
 */
@Component
@ConfigurationProperties(prefix = "wushi.tenant")
public class TenantProperties {

    /**
     * 是否使用多租户
     **/
    private boolean enabled = false;

    /**
     * 数据库表里租户字段名称
     **/
    private String fieldName = "tenant_id";

    /**
     * 不需要自动添加租户过滤的表
     **/
    private List<String> excludeTable;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getExcludeTable() {
        return excludeTable;
    }

    public void setExcludeTable(List<String> excludeTable) {
        this.excludeTable = excludeTable;
    }
}
