package com.wushi.lade.common.parameter;

import javax.validation.Valid;

/**
 * 分页参数类
 *
 * @author wushi
 */
public class Paging<T> {
    private int pageIndex;
    private int pageSize;
    @Valid
    private T request;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
}
