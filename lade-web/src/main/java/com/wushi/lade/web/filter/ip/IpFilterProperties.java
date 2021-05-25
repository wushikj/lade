package com.wushi.lade.web.filter.ip;


import com.wushi.lade.web.enums.IpFilterMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Junior
 */
@ConfigurationProperties(prefix = "wushi.security.ip-filter", ignoreUnknownFields = false)
public class IpFilterProperties {
    public IpFilterMode getMode() {
        return mode;
    }

    public void setMode(IpFilterMode mode) {
        this.mode = mode;
    }

    public String getAllowed() {
        return allowed;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    /**
     * IP过滤模式，有白名单和黑名单方式
     * 默认：不过滤
     */
    private IpFilterMode mode = IpFilterMode.NONE;

    /**
     * 白名单过滤规则
     * 当mode为IP_FILTER_MODE.ALLOWED时生效
     * 支持IP段（192.168.1.*;192.168.1.100-192.168.1.200）、多个IP配置(用英文","间隔)，
     * 默认：所有的IP都不可访问
     */
    private String allowed;
    /**
     * 黑名单过滤规则
     * 当mode为IP_FILTER_MODE.BLOCKED时生效
     * 支持IP段（192.168.1.*、192.168.1.100-192.168.1.200，使用“-”表示的）、多个IP配置(用英文","间隔)，
     * 默认：所有的IP都可以访问
     */
    private String blocked;


}
