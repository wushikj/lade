package com.wushi.lade.dao.interfaces;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseDao<M extends BaseMapper<T>,T> extends IService<T> {

}
