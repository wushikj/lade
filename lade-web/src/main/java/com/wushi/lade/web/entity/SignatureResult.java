package com.wushi.lade.web.entity;

import com.wushi.lade.common.enums.ErrorCode;

/**
 * @author wushi
 * @date 2020/3/16 14:27
 * @description
 */
public class SignatureResult {
    /**
     * 验证是否成功
     */
    private Boolean success;

    /**
     * 具体错误信息
     */
    private String message;

    /**
     * 错误码
     */
    private ErrorCode errorCode;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.wushi.lade.common.enums.ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(com.wushi.lade.common.enums.ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
