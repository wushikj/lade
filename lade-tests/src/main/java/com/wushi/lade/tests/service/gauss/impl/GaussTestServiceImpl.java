package com.wushi.lade.tests.service.gauss.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.gauss.TestByGauss;
import com.wushi.lade.tests.mapper.gauss.GaussTestMapper;
import com.wushi.lade.tests.service.gauss.GaussService;
import org.springframework.stereotype.Service;

/**
 * @author wushi
 * @date 2020/1/20 16:26
 */
@Service
@DS("gauss")
public class GaussTestServiceImpl extends ServiceImpl<GaussTestMapper, TestByGauss> implements GaussService {
}
