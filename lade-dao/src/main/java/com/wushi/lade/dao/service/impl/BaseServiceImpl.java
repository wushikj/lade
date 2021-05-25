package com.wushi.lade.dao.service.impl;

import com.wushi.lade.dao.interfaces.BaseDao;
import com.wushi.lade.dao.service.BaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * BaseService服务实现类
 *
 * @author wushi
 * @date 2020-07-31
 */
@Service
public class BaseServiceImpl<D extends BaseDao,T> implements BaseService<T> {

    protected D dao;

    @Override
    public T getById(Serializable id) {
        return (T)dao.getById(id);
    }

    @Override
    public boolean save(T entity) {
        return dao.save(entity);
    }

    @Override
    public boolean update(T entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        return dao.saveOrUpdate(entity);
    }

    @Override
    public boolean removeById(String id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(List<String> ids) {
        return dao.removeByIds(ids);
    }
}
