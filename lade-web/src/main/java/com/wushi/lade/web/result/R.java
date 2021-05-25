package com.wushi.lade.web.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.wushi.lade.common.enums.ErrorCode;
import com.wushi.lade.common.interfaces.BaseErrorCode;
import com.wushi.lade.web.result.model.ErrorModel;
import com.wushi.lade.web.result.model.PagingResult;
import com.wushi.lade.web.result.model.PagingSummary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author wushi
 * @date 2021/3/24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("统一响应信息")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功失败共享
     */
    @ApiModelProperty(value = "链路ID")
    private String traceId = MDC.get("traceId");

    @ApiModelProperty(value = "操作是否成功")
    private boolean success;

    @ApiModelProperty(value = "时间戳")
    private Long timestamp = System.currentTimeMillis();

    /*   成功消息体   */

    /**
     * 分页信息
     */
    @ApiModelProperty(value = "分页信息")
    private PagingSummary paging;

    /**
     * 数据
     */
    @ApiModelProperty(value = "返回结果数据")
    private T data;

    /*   失败消息体   */

    private ErrorModel error;

    /**
     * null值字段是否返回
     */
    @JsonIgnore
    @ApiModelProperty(value = "null值字段是否返回", hidden = true)
    private JsonInclude.Include includeNullField;

    /**
     * 序列化配置
     **/

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private SerializationConfig serializeConfig;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private SimpleMixInResolver mixIns;


    /*
     * 返回成功消息封装
     */

    public static <T> R<T> ok() {
        return ok(null, null, null, null);
    }

    public static <T> R<T> ok(T data) {
        return ok(data, null, null, null);
    }

    public static <T> R<T> ok(T data, JsonInclude.Include includeNullField) {
        return ok(data, null, null, includeNullField);
    }

    public static <T> R<T> ok(T data, String msg) {
        return ok(data, msg, null, null);
    }

    public static <T> R<T> ok(T data, String msg, JsonInclude.Include includeNullField) {
        return ok(data, msg, null, includeNullField);
    }

    public static <T> R<T> ok(PagingResult<T> pagingResult) {
        return ok(pagingResult.getRecords(), null, pagingResult, null);
    }

    public static <T> R<T> ok(PagingResult<T> pagingResult, String msg) {
        return ok(pagingResult.getRecords(), msg, pagingResult, null);
    }

    public static <T> R<T> ok(PagingResult<T> pagingResult, String msg, JsonInclude.Include includeNullField) {
        return ok(pagingResult.getRecords(), msg, pagingResult, includeNullField);
    }

    public static <T> R<T> ok(PagingResult<T> pagingResult, JsonInclude.Include includeNullField) {
        return ok(pagingResult.getRecords(), null, pagingResult, includeNullField);
    }

    public static <T> R<T> ok(T data, PagingSummary pagingSummary) {
        return ok(data, null, pagingSummary, null);
    }

    public static <T> R<T> ok(T data, PagingSummary pagingSummary, JsonInclude.Include includeNullField) {
        return ok(data, null, pagingSummary, includeNullField);
    }

    public static <T> R<T> ok(T data, String msg, PagingSummary pagingSummary) {
        return ok(data, msg, pagingSummary, null);
    }

    public static <T> R<T> ok(T data, String msg, PagingSummary pagingSummary, JsonInclude.Include includeNullField) {
        return ok(data, msg, pagingSummary, includeNullField, null, null);
    }

    public static <T> R<T> ok(T data, String msg, PagingSummary pagingSummary, JsonInclude.Include includeNullField, SerializationConfig serializationConfig) {
        return ok(data, msg, pagingSummary, includeNullField, serializationConfig, null);
    }

    public static <T> R<T> ok(T data, String msg, PagingSummary pagingSummary, JsonInclude.Include includeNullField, SerializationConfig serializationConfig, SimpleMixInResolver mixInResolver) {
        R<T> apiResult = new R<>();
        apiResult.setSuccess(true);
        apiResult.setData(data);
        apiResult.setPaging(pagingSummary);
        apiResult.setIncludeNullField(includeNullField);
        apiResult.setSerializeConfig(serializationConfig);
        apiResult.setMixIns(mixInResolver);
        return apiResult;
    }

    /*
     * 返回失败消息封装
     */

    public static <T> R<T> fail(BaseErrorCode errorCode) {
        return fail(errorCode, null, HttpStatus.OK);
    }

    public static <T> R<T> fail(String msg) {
        return fail(ErrorCode.Http_InternalServerError, msg, HttpStatus.OK);
    }

    public static <T> R<T> fail(BaseErrorCode errorCode, String msg) {
        return fail(errorCode, msg, HttpStatus.OK);
    }

    public static <T> R<T> fail(BaseErrorCode errorCode, String msg, HttpStatus httpStatus) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setMessage(msg);
        errorModel.setErrorName(errorCode.toString());
        errorModel.setErrorCode(errorCode.getErrorCode());
        errorModel.setErrorText(errorCode.getErrorText());
        HttpServletRequest request = getRequest();
        errorModel.setMethod(request != null ? request.getMethod() : "");
        errorModel.setStatusCode(httpStatus.value());

        R<T> apiResult = new R<>();
        apiResult.setSuccess(false);
        apiResult.setError(errorModel);
        return apiResult;
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

}