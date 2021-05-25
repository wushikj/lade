package com.wushi.lade.cache.autoconfiguration;

import com.wushi.lade.cache.properties.CacheProperties;
import com.wushi.lade.cache.properties.EhCacheProperties;
import com.wushi.lade.cache.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 统一缓存配置自动装载
 *
 * @author wushi
 * @date 2020/1/15 14:41
 */
@Configuration
@Import({LettuceConnectionConfiguration.class})
@EnableConfigurationProperties({CacheProperties.class, EhCacheProperties.class, RedisProperties.class})
public class CacheAutoConfiguration {
}
