package com.wushi.lade.dao.plugins.permission;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author wushi
 * @date 2020/9/28 14:53
 */
public interface DataFilterConfig {

    void set(PaginationInterceptor paginationInterceptor);
}
