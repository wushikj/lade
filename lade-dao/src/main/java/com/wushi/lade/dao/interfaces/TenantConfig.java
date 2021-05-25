package com.wushi.lade.dao.interfaces;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author wushi
 * @date 2020/1/20 14:50
 * @description
 */
public interface TenantConfig {
    void set(PaginationInterceptor paginationInterceptor);
}
