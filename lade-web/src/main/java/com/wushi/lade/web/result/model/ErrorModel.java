package com.wushi.lade.web.result.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页信息
 *
 * @author wushi
 * @date 2021/3/26
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "错误消息体")
public class ErrorModel {
    /**
     * 消息
     */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 请求的Http方法
     */
    @ApiModelProperty(value = "请求的Http方法")
    private String method;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int statusCode;

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
     * 堆栈调用路径
     */
    @ApiModelProperty(value = "堆栈调用路径")
    private String stack;
}
