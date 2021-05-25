package com.wushi.lade.web.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 全局异常信息对象
 *
 * @author wushi
 */
@ApiModel(value = "错误信息")
public class ErrorMessageContent implements Serializable {

    /**
     * 当前请求的ID
     */
    @ApiModelProperty(value = "当前请求的ID")
    private String httpTraceId;

    /**
     * 请求的Http方法
     */
    @ApiModelProperty(value = "请求的Http方法")
    private String httpMethod;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int httpStatusCode;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码")
    private int errorCode;

    /**
     * 错误码名称
     */
    @ApiModelProperty(value = "错误码名称")
    private String errorName;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorText;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String message;

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

    /**
     * 堆栈调用路径
     */
    private String stack;

    /**
     * 创建错误消息内容
     */
    public ErrorMessageContent() {
        HttpServletRequest request = getRequest();
        this.httpTraceId = (String) request.getAttribute("traceId");
        this.httpMethod = request.getMethod();
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    /**
     * 创建错误消息内容
     *
     * @param request  请求
     * @param response 响应
     */
    public ErrorMessageContent(HttpServletRequest request, HttpServletResponse response) {
        this.httpTraceId = (String) request.getAttribute("traceId");
        this.httpMethod = request.getMethod();
        this.httpStatusCode = response.getStatus();
    }


    /**
     * 创建错误消息内容
     *
     * @param errorCode 错误码
     * @param errorName 错误名称
     * @param errorText 错误码描述
     */
    public ErrorMessageContent(int errorCode, String errorName, String errorText) {
        this();
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorText = errorText;
    }


    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
