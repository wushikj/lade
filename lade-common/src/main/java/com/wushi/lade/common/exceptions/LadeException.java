package com.wushi.lade.common.exceptions;

import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.interfaces.BaseErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Lade基础异常
 *
 * @author wushi
 * @date 2019/11/29
 */
class LadeException extends RuntimeException {

    /**
     * 错误编码
     */
    private int errorCode;

    /**
     * 错误名称
     */
    private String errorName;

    /**
     * 错误文档
     */
    private String errorText;

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

    /**
     * 创建LadeException对象
     *
     * @param errorCode 自定义错误码
     * @param errorName 自定义错误码名称
     * @param errorText 自定义错误描述
     * @param message   自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param exception 异常对象
     * @see java.lang.Exception
     */
    public LadeException(int errorCode, String errorName, String errorText, String message, Exception exception) {
        super(StringUtils.isBlank(message) ? errorText : message, exception);
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorText = errorText;
    }

    /**
     * 创建LadeException对象
     *
     * @param errorCode 自定义错误码
     * @param errorName 自定义错误码名称
     * @param errorText 自定义错误描述
     * @param exception 异常对象
     * @see java.lang.Exception
     */
    public LadeException(int errorCode, String errorName, String errorText, Exception exception) {
        super(errorText, exception);
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorText = errorText;
    }

    /**
     * 创建LadeException对象
     *
     * @param errorCode 自定义错误码
     * @param errorName 自定义错误码名称
     * @param errorText 自定义错误描述
     */
    public LadeException(int errorCode, String errorName, String errorText) {
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorText = errorText;
    }

    /**
     * 创建LadeException对象
     */
    public LadeException(String message, Exception ex) {
        super(message, ex);
    }

    /**
     * 创建LadeException对象
     *
     * @param errorCode 错误枚举
     * @see ErrorCode
     */
    public LadeException(BaseErrorCode errorCode) {
        this.errorCode = errorCode.getErrorCode();
        this.errorName = errorCode.toString();
        this.errorText = errorCode.getErrorText();
    }

    /**
     * 创建LadeException对象
     *
     * @param errorCode 错误枚举
     * @see ErrorCode
     */
    public LadeException(BaseErrorCode errorCode, Exception ex) {
        super(errorCode.getErrorText(), ex);
        this.errorCode = errorCode.getErrorCode();
        this.errorName = errorCode.toString();
        this.errorText = errorCode.getErrorText();
    }

    /**
     * 创建LadeException对象
     *
     * @param errorCode 错误枚举
     * @param message   自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @see ErrorCode
     */
    public LadeException(BaseErrorCode errorCode, String message, Exception ex) {
        super(message, ex);
        this.errorCode = errorCode.getErrorCode();
        this.errorName = errorCode.toString();
        this.errorText = errorCode.getErrorText();
    }
}