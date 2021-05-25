package com.wushi.lade.cache.properties;

import com.wushi.lade.cache.enums.CacheTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 统一缓存配置
 *
 * @author wushi
 * @date 2020/1/13 13:59
 */
@ConfigurationProperties(prefix = "wushi.cache")
public class CacheProperties {

    /**
     * 缓存类型配置，默认ehcache
     */
    private CacheTypeEnum type = CacheTypeEnum.EhCache;

    public CacheTypeEnum getType() {
        return type;
    }

    public void setType(CacheTypeEnum type) {
        this.type = type;
    }
}
