package com.wushi.lade.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.wushi.lade.common.exceptions.BusinessException;
import com.wushi.lade.common.interfaces.BaseErrorCode;
import com.wushi.lade.web.result.ContentResult;
import com.wushi.lade.web.result.FileResult;
import com.wushi.lade.web.result.R;
import com.wushi.lade.web.result.model.ErrorModel;
import com.wushi.lade.web.result.model.PagingResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.FileInputStream;

/**
 * Api基础控制器
 *
 * @author wushi
 */
public class BaseController {

    /**
     * 返回成功信息
     *
     * @param data 返回数据
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(T data) {
        return successResult(data, null);
    }

    /**
     * 返回成功信息
     *
     * @param pagingResult 分页数据
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(PagingResult<T> pagingResult) {
        return successResult(pagingResult.getRecords(), pagingResult);
    }

    /**
     * 返回成功信息
     *
     * @param data         返回数据
     * @param pagingResult 分页信息
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(T data, PagingResult pagingResult) {
        return successResult(data, pagingResult, null);
    }


    /**
     * 返回成功信息
     *
     * @param data         返回数据
     * @param pagingResult 分页信息
     * @param message      返回说明
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(T data, PagingResult pagingResult, String message) {
        return successResult(data, pagingResult, message, JsonInclude.Include.ALWAYS);
    }

    /**
     * 返回成功信息
     *
     * @param data             返回数据
     * @param pagingResult     分页信息
     * @param message          返回说明
     * @param includeNullField null值字段是否返回
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(T data, PagingResult pagingResult, String message, JsonInclude.Include includeNullField) {
        return successResult(data, pagingResult, message, includeNullField, null);
    }

    /**
     * 返回成功信息
     *
     * @param data             返回数据
     * @param pagingResult     分页信息
     * @param message          返回说明
     * @param includeNullField 是否输出null字段
     * @param serializeConfig  序列化配置信息
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(T data, PagingResult pagingResult, String message, JsonInclude.Include includeNullField, SerializationConfig serializeConfig) {
        return R.ok(data, message, pagingResult, includeNullField, serializeConfig, null);
    }

    /**
     * 返回成功信息
     *
     * @param data             返回数据
     * @param pagingResult     分页信息
     * @param message          返回说明
     * @param includeNullField 是否输出null字段
     * @param serializeConfig  序列化配置信息
     * @param mixInResolver
     * @return R
     * @author wushi
     * @date 2020/3/20 11:22
     * @description
     */
    public <T> R<T> successResult(T data, PagingResult pagingResult, String message, JsonInclude.Include includeNullField, SerializationConfig serializeConfig, SimpleMixInResolver mixInResolver) {
        return R.ok(data, message, pagingResult, includeNullField, serializeConfig, mixInResolver);
    }


    /**
     * 返回错误信息
     *
     * @param errorCode  继承BaseErrorCode的枚举
     * @param httpStatus http状态码
     * @return R
     * @author wushi
     * @date 2020/3/20 13:18
     * @description
     */
    public R errorResult(BaseErrorCode errorCode, HttpStatus httpStatus) {
        return errorResult(errorCode.getErrorCode(), errorCode.toString(), errorCode.getErrorText(), httpStatus);
    }

    /**
     * 返回错误信息
     *
     * @param errorCode 继承BaseErrorCode的枚举
     * @return R
     * @author wushi
     * @date 2020/3/20 13:18
     * @description
     */
    public R errorResult(BaseErrorCode errorCode) {
        return errorResult(errorCode.getErrorCode(), errorCode.toString(), errorCode.getErrorText());
    }


    /**
     * 返回错误信息
     *
     * @param errorCode 错误码
     * @param errorName 错误名
     * @param errorText 错误说明
     * @return R
     * @author wushi
     * @date 2020/3/20 13:18
     * @description
     */
    public R errorResult(int errorCode, String errorName, String errorText) {
        return errorResult(errorCode, errorName, errorText, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 返回错误信息
     *
     * @param errorCode  错误码
     * @param errorName  错误名
     * @param errorText  错误说明
     * @param httpStatus http状态码
     * @return R
     * @author wushi
     * @date 2020/3/20 13:18
     * @description
     */
    public R errorResult(int errorCode, String errorName, String errorText, HttpStatus httpStatus) {
        R r = new R();
        ErrorModel model = new ErrorModel();
        model.setErrorCode(errorCode);
        model.setErrorName(errorName);
        model.setErrorText(errorText);
        model.setStatusCode(httpStatus.value());
        r.setError(model);
        return r;
    }


    /**
     * 错误处理
     *
     * @param errorCode 自定义错误编码
     * @param errorName 自定义错误名称
     * @param errorText 自定义错误消息
     * @param message   自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param exception 异常对象 @see java.lang.Exception
     * @throws BusinessException 业务异常
     */
    public void throwError(int errorCode, String errorName, String errorText, String message, Exception exception) throws BusinessException {
        throw new BusinessException(errorCode, errorName, errorText, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception);
    }

    /**
     * 错误处理
     *
     * @param errorCode 错误枚举 @see com.wushi.lade.abstractions.enums.ErrorCode
     * @param message   自定义异常信息，用于替换exception对象中的Message属性值，为空时使用errorText替换
     * @param exception 异常对象 @see java.lang.Exception
     * @throws BusinessException 业务异常
     */
    public void throwError(BaseErrorCode errorCode, String message, Exception exception) throws BusinessException {
        throw new BusinessException(errorCode, message, exception);
    }

    /**
     * 错误处理
     *
     * @param errorCode 错误枚举 @see com.wushi.lade.abstractions.enums.ErrorCode
     * @throws BusinessException 业务异常
     */
    public void throwError(BaseErrorCode errorCode) throws BusinessException {
        throw new BusinessException(errorCode);
    }

    /**
     * 错误处理
     *
     * @param errorCode 错误枚举 @see com.wushi.lade.abstractions.enums.ErrorCode
     * @throws BusinessException 业务异常
     */
    public void throwError(BaseErrorCode errorCode, Integer httpStatus) throws BusinessException {
        throw new BusinessException(errorCode, httpStatus);
    }

    /**
     * 返回html
     *
     * @param html html字符串
     * @return void
     * @author wushi
     * @date 2020/1/3 10:37
     */
    public ContentResult htmlResult(String html) {
        return new ContentResult(html, MediaType.TEXT_HTML_VALUE);
    }

    /**
     * 返回xml
     *
     * @param xml xml字符串
     * @return void
     * @author wushi
     * @date 2020/1/7 17:05
     */
    public ContentResult xmlResult(String xml) {
        return new ContentResult(xml, MediaType.APPLICATION_XML_VALUE);
    }

    /**
     * 自定义返回文本
     *
     * @param content     文本内容
     * @param contentType 文本类型
     * @return R
     * @author wushi
     * @date 2020/3/20 13:53
     * @description
     */
    public ContentResult contentResult(String content, String contentType) {
        return new ContentResult(content, contentType);
    }


    /**
     * 下载文件
     *
     * @param fileInputStream 文件输入流
     * @param contentType     mime类型
     * @return void
     * @author wushi
     * @date 2020/1/7 17:11
     */
    public FileResult fileResult(FileInputStream fileInputStream, String fileName, String contentType) {
        return new FileResult(fileInputStream, fileName, contentType);
    }

    /**
     * 在浏览器浏览文件
     *
     * @param fileInputStream 文件输入流
     * @param contentType     mime类型
     * @return void
     * @author wushi
     * @date 2020/1/7 17:11
     */
    public FileResult fileResult(FileInputStream fileInputStream, String contentType) {
        return new FileResult(fileInputStream, contentType);
    }

    /**
     * 在浏览器浏览文件
     *
     * @param fileInputStream 文件输入流
     * @return void
     * @author wushi
     * @date 2020/1/7 17:11
     */
    public FileResult fileResult(FileInputStream fileInputStream) {
        return new FileResult(fileInputStream, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }
}
