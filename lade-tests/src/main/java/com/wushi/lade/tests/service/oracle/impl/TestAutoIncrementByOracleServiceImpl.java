package com.wushi.lade.tests.service.oracle.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.oracle.TestAutoIncrementByOracle;
import com.wushi.lade.tests.mapper.oracle.TestAutoIncrementByOracleMapper;
import com.wushi.lade.tests.service.oracle.ITestAutoIncrementByOracleService;
import org.springframework.stereotype.Service;

/**
 * TestAutoIncrement 服务实现类
 *
 * @author wushi
 * @since 2020-01-08 13:52
 */
@Service
@DS("oracle")
public class TestAutoIncrementByOracleServiceImpl extends ServiceImpl<TestAutoIncrementByOracleMapper, TestAutoIncrementByOracle> implements ITestAutoIncrementByOracleService {

}
