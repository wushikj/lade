package com.wushi.lade.logging.mix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 提交到运维监控平台的数据
 *
 * @author wushi
 * @date 2021/1/26 15:01
 */
public class LogContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 纳秒
     */
    @JsonProperty(value = "batch_id")
    private String batchId;

    private LogIdentity identity;
    /**
     * 毫秒
     */
    private long time;

    private String level;

    /**
     * tags的内容可选
     */
    private List<String> tags;

    private String content;

    private String category;

    @JsonProperty(value = "raw_data")
    private Object rawData;

    /**
     * 备注
     */
    private String remark;

    private String priority;

    private String env;

    private LogSource source;

    public String getBatchId() {
        return batchId;
    }

    public LogIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(LogIdentity identity) {
        this.identity = identity;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getRawData() {
        return rawData;
    }

    public void setRawData(Object rawData) {
        this.rawData = rawData;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public LogSource getSource() {
        return source;
    }

    public void setSource(LogSource source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "LogContent{" +
                "batchId='" + batchId + '\'' +
                ", identity=" + identity +
                ", time=" + time +
                ", level='" + level + '\'' +
                ", tags=" + tags +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", rawData=" + rawData +
                ", remark='" + remark + '\'' +
                ", priority='" + priority + '\'' +
                ", env='" + env + '\'' +
                ", source=" + source +
                '}';
    }
}
