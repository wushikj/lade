package com.wushi.lade.cache.autoconfiguration;

import com.wushi.lade.cache.properties.EhCacheProperties;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * EhCache缓存配置
 *
 * @author wushi
 * @date 2020/1/15 11:25
 */
@Configuration
@ConditionalOnProperty(prefix = "wushi.cache", name = "type", havingValue = "ehcache", matchIfMissing = true)
public class EhCacheAutoConfiguration {

    @Resource
    private EhCacheProperties ehCacheProperties;

    public static final String CACHE_NAME = "ec_ehcache";

    @Bean
    public CacheManager cacheManager() {
        // 创建缓存配置
        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setEternal(ehCacheProperties.getEternal());
        cacheConfiguration.setMaxEntriesLocalHeap(ehCacheProperties.getMaxElementsInMemory());
        cacheConfiguration.setOverflowToOffHeap(ehCacheProperties.getOverflowToDisk());
        cacheConfiguration.setTimeToIdleSeconds(ehCacheProperties.getTimeToIdleSeconds());
        cacheConfiguration.setTimeToLiveSeconds(ehCacheProperties.getTimeToLiveSeconds());
        cacheConfiguration.setName(CACHE_NAME);
        configuration.setDefaultCacheConfiguration(cacheConfiguration);
        configuration.setName(CACHE_NAME);
        // 创建一个缓存管理器
        CacheManager cacheManager = CacheManager.create(configuration);
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if(cache == null) {
            cacheManager.addCache(CACHE_NAME);
        }
        return cacheManager;
    }
}
