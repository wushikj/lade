package com.wushi.lade.common.exceptions;

import com.wushi.lade.common.interfaces.BaseErrorCode;

/**
 * 业务异常
 *
 * @author wushi
 */
public class BusinessException extends LadeException {

    /**
     * http状态码 org.springframework.http.HttpStatus
     **/
    private Integer httpStatus;

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }
    /**
     * 业务异常
     *
     * @param message 自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @see java.lang.Exception
     */
    public BusinessException(String message) {
        super(message, null);
    }

    /**
     * 业务异常
     *
     * @param message   自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param exception 异常对象
     * @see java.lang.Exception
     */
    public BusinessException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * 业务异常
     *
     * @param message    自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param httpStatus http状态码
     * @see java.lang.Exception
     */
    public BusinessException(String message, Integer httpStatus) {
        super(message, null);
        this.httpStatus = httpStatus;
    }

    /**
     * 业务异常
     *
     * @param message    自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param httpStatus http状态码
     * @param exception  异常对象
     * @see java.lang.Exception
     */
    public BusinessException(String message, Integer httpStatus, Exception exception) {
        super(message, exception);
        this.httpStatus = httpStatus;
    }

    /**
     * 业务异常
     *
     * @param errorCode 自定义错误编码
     * @param errorName 自定义错误码名称
     * @param errorText 自定义错误文本，用于替换掉ErrorCode对象的ErrorText属性值
     */
    public BusinessException(int errorCode, String errorName, String errorText) {
        super(errorCode, errorName, errorText);
    }

    /**
     * 业务异常
     *
     * @param errorCode  自定义错误编码
     * @param errorName  自定义错误码名称
     * @param errorText  自定义错误文本，用于替换掉ErrorCode对象的ErrorText属性值
     * @param httpStatus http状态码
     */
    public BusinessException(int errorCode, String errorName, String errorText, Integer httpStatus) {
        super(errorCode, errorName, errorText);
        this.httpStatus = httpStatus;
    }

    /**
     * 业务异常
     *
     * @param errorCode  自定义错误编码
     * @param errorName  自定义错误码名称
     * @param errorText  自定义错误文本，用于替换掉ErrorCode对象的ErrorText属性值
     * @param message    自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param httpStatus http状态码
     * @param exception  异常对象
     * @see java.lang.Exception
     */
    public BusinessException(int errorCode, String errorName, String errorText, String message, Integer httpStatus, Exception exception) {
        super(errorCode, errorName, errorText, message, exception);
        this.httpStatus = httpStatus;
    }

    /**
     * 业务异常
     *
     * @param errorCode 错误枚举
     * @see BaseErrorCode
     */
    public BusinessException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 业务异常
     *
     * @param errorCode  错误枚举
     * @param httpStatus http状态码
     * @see BaseErrorCode
     */
    public BusinessException(BaseErrorCode errorCode, Integer httpStatus) {
        super(errorCode);
        this.httpStatus = httpStatus;
    }


    /**
     * 业务异常
     *
     * @param errorCode 错误枚举
     * @param exception 异常对象
     * @see BaseErrorCode
     * @see java.lang.Exception
     */
    public BusinessException(BaseErrorCode errorCode, Exception exception) {
        super(errorCode, exception);
    }

    /**
     * 业务异常
     *
     * @param errorCode 错误枚举
     * @param message   自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param exception 异常对象
     * @see BaseErrorCode
     * @see java.lang.Exception
     */
    public BusinessException(BaseErrorCode errorCode, String message, Exception exception) {
        super(errorCode, message, exception);
    }

    /**
     * 业务异常
     *
     * @param errorCode  错误枚举
     * @param message    自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param httpStatus http状态码
     * @param exception  异常对象
     * @see BaseErrorCode
     * @see java.lang.Exception
     */
    public BusinessException(BaseErrorCode errorCode, String message, Integer httpStatus, Exception exception) {
        super(errorCode, message, exception);
        this.httpStatus = httpStatus;
    }
}
