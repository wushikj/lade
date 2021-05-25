package com.wushi.lade.dao.properties;

import com.wushi.lade.dao.plugins.permission.DataFilterStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author wushi
 * @date 2020/9/28 15:13
 */
@Component
@ConfigurationProperties(prefix = "wushi.data-filter")
public class DataFilterProperties {

    /**
     * 是否启用数据权限
     */
    private Boolean enabled;

    /**
     * 不需要进行数据过滤的表名
     */
    private Set<String> excludeTable;

    /**
     * 用户没数据权限时过滤策略
     */
    private DataFilterStrategy strategy;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getExcludeTable() {
        return excludeTable;
    }

    public void setExcludeTable(Set<String> excludeTable) {
        this.excludeTable = excludeTable;
    }

    public DataFilterStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(DataFilterStrategy strategy) {
        this.strategy = strategy;
    }
}
