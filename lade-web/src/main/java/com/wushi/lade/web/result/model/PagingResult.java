package com.wushi.lade.web.result.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分页信息
 *
 * @author wushi
 * @date 2021/3/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "分页信息")
public class PagingResult<T> extends PagingSummary{

    @JsonIgnore
    @ApiModelProperty(value = "数据", hidden = true)
    private T records;

}
