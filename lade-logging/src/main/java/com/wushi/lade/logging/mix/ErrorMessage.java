package com.wushi.lade.logging.mix;

import java.io.Serializable;

/**
 * 全局异常信息对象
 *
 * @author wushi
 */
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前请求的ID
     */
    private String httpTraceId;

    /**
     * 请求的Http方法
     */
    private String httpMethod;

    /**
     * 状态码
     */
    private int httpStatusCode;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误码名称
     */
    private String errorName;

    /**
     * 错误信息
     */
    private String errorText;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 堆栈调用路径
     */
    private String stack;

    public String getHttpTraceId() {
        return httpTraceId;
    }

    public void setHttpTraceId(String httpTraceId) {
        this.httpTraceId = httpTraceId;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "httpTraceId='" + httpTraceId + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", errorCode=" + errorCode +
                ", errorName='" + errorName + '\'' +
                ", errorText='" + errorText + '\'' +
                ", message='" + message + '\'' +
                ", stack='" + stack + '\'' +
                '}';
    }
}
