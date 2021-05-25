package com.wushi.lade.logging.mix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author wushi
 * @date 2021/1/26 16:45
 */
public class LogIdentity implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("project_id")
    private String projectId;
    @JsonProperty("target_ip")
    private String targetIp;

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

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    @Override
    public String toString() {
        return "LogIdentity{" +
                "customerId='" + customerId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", targetIp='" + targetIp + '\'' +
                '}';
    }
}
