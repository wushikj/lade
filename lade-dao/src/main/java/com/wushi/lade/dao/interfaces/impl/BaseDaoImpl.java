package com.wushi.lade.dao.interfaces.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.dao.interfaces.BaseDao;

/**
 * @author wushi
 */
public class BaseDaoImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements BaseDao<M,T> {

}
