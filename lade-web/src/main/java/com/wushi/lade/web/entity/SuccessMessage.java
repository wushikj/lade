package com.wushi.lade.web.entity;

import java.io.Serializable;

/**
 * 成功消息
 *
 * @author wushi
 */
public class SuccessMessage implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 结构方案
     */
    private Object schema;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getSchema() {
        return schema;
    }

    public void setSchema(Object schema) {
        this.schema = schema;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getPaging() {
        return paging;
    }

    public void setPaging(Object paging) {
        this.paging = paging;
    }

    /**
     * 分页信息
     */
    private Object paging;

}
