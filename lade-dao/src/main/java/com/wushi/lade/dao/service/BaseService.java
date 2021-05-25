package com.wushi.lade.dao.service;

import com.wushi.lade.common.entity.PagingResult;
import com.wushi.lade.common.parameter.Paging;

import java.io.Serializable;
import java.util.List;

/**
 * Service接口
 *
 * @author wushi
 * @date 2020-07-31
 */
public interface BaseService<T> {

    /**
     * T
     * 根据id获取记录
     *
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 保存记录
     *
     * @param entity
     * @return
     */
    boolean save(T entity);

    /**
     * 修改记录
     *
     * @param entity
     * @return
     */
    boolean update(T entity);

    /**
     * 保存或修改记录
     *
     * @param entity
     * @return
     */
    boolean saveOrUpdate(T entity);

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return
     */
    boolean removeById(String id);

    /**
     * 删除多条记录
     *
     * @param ids
     * @return
     */
    boolean removeByIds(List<String> ids);

    /**
     * 分页
     *
     * @param request
     * @return
     */
    default PagingResult<T> page(Paging<T> request) {
        return null;
    }

}
