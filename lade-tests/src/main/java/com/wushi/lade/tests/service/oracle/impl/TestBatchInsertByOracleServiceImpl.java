package com.wushi.lade.tests.service.oracle.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.oracle.TestBatchInsertByOracle;
import com.wushi.lade.tests.mapper.oracle.TestBatchInsertByOracleMapper;
import com.wushi.lade.tests.service.oracle.ITestBatchInsertByOracleService;
import org.springframework.stereotype.Service;

/**
 * TestBatchInsert 服务实现类
 *
 * @author wushi
 * @since 2020-01-08 13:52
 */
@Service
@DS("oracle")
public class TestBatchInsertByOracleServiceImpl extends ServiceImpl<TestBatchInsertByOracleMapper, TestBatchInsertByOracle> implements ITestBatchInsertByOracleService {

}
