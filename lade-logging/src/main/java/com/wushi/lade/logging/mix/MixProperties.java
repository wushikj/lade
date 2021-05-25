package com.wushi.lade.logging.mix;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wushi
 * @date 2021/3/18 9:51
 */
@ConfigurationProperties(prefix = "wushi.mix")
public class MixProperties {

    /**
     * 客户编码
     */
    private String customerId;

    /**
     * 项目编码
     */
    private String projectId;

    /**
     * 日志上报接口
     */
    private String mixEndpoint;

    /**
     * 上报密钥
     */
    private String mixEndpointKey;

    /**
     * 请求超时配置，毫秒
     */
    private String timeout;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMixEndpoint() {
        return mixEndpoint;
    }

    public void setMixEndpoint(String mixEndpoint) {
        this.mixEndpoint = mixEndpoint;
    }

    public String getMixEndpointKey() {
        return mixEndpointKey;
    }

    public void setMixEndpointKey(String mixEndpointKey) {
        this.mixEndpointKey = mixEndpointKey;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
