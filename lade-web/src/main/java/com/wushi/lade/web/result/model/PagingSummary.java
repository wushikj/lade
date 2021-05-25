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
@ApiModel(value = "分页摘要")
public class PagingSummary {

    @ApiModelProperty(value = "当前页")
    private long pageIndex;

    @ApiModelProperty(value = "单页显示记录数")
    private long pageSize;

    @ApiModelProperty(value = "总页数")
    private long totalPage;

    @ApiModelProperty(value = "记录总数")
    private long totalRecord;
}
