package com.wushi.lade.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wushi
 * @date 2020/5/20 17:34
 */
@Deprecated
@ApiModel(value = "分页信息")
public class PagingResult<T> {
    @ApiModelProperty(value = "当前页")
    private long pageIndex;
    @ApiModelProperty(value = "单页显示记录数")
    private long pageSize;
    @ApiModelProperty(value = "总页数")
    private long totalPage;
    @ApiModelProperty(value = "记录总数")
    private long totalRecord;
    @JsonIgnore
    @ApiModelProperty(value = "数据", hidden = true)
    private List<T> records;

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public PagingResult() {
    }

    public PagingResult(long pageIndex, long pageSize, long totalPage, long totalRecord, List<T> records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
        this.records = records;
    }
}
