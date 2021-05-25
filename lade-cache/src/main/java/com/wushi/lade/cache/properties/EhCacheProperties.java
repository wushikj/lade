package com.wushi.lade.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * EhCache配置文件
 *
 * @author wushi
 * @date 2020/1/15 9:21
 */
@ConfigurationProperties(prefix = "wushi.cache.ehcache")
public class EhCacheProperties {
    /**
     * 对象是否永久有效（设置为true，则timeout将不起作用）
     */
    private Boolean eternal = false;

    /**
     * 缓存最大数目
     */
    private int maxElementsInMemory = 1000;

    /**
     * 当系统宕机时，是否保存到磁盘(需要企业版ehcache支持)
     */
    private Boolean overflowToDisk = false;

    /**
     * 设置对象在失效前的允许闲置时间（单位：秒）【滑动过期】
     * 默认值为0，代表时间无限大
     */
    private int timeToIdleSeconds = 0;

    /**
     * 设置对象在失效前允许存活时间（单位：秒）
     * 默认值为0，代表时间无限大
     */
    private int timeToLiveSeconds = 0;

    public Boolean getEternal() {
        return eternal;
    }

    public void setEternal(Boolean eternal) {
        this.eternal = eternal;
    }

    public int getMaxElementsInMemory() {
        return maxElementsInMemory;
    }

    public void setMaxElementsInMemory(int maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
    }

    public Boolean getOverflowToDisk() {
        return overflowToDisk;
    }

    public void setOverflowToDisk(Boolean overflowToDisk) {
        this.overflowToDisk = overflowToDisk;
    }

    public int getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }

    public void setTimeToIdleSeconds(int timeToIdleSeconds) {
        this.timeToIdleSeconds = timeToIdleSeconds;
    }

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }
}
