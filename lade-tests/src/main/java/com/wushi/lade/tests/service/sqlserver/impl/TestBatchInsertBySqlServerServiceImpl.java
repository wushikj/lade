package com.wushi.lade.tests.service.sqlserver.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.sqlserver.TestBatchInsertBySqlServer;
import com.wushi.lade.tests.mapper.sqlserver.TestBatchInsertBySqlServerMapper;
import com.wushi.lade.tests.service.sqlserver.ITestBatchInsertBySqlServerService;
import org.springframework.stereotype.Service;

/**
 * TestBatchInsert 服务实现类
 *
 * @author wushi
 * @since 2020-01-08 13:52
 */
@Service
@DS("sqlserver")
public class TestBatchInsertBySqlServerServiceImpl extends ServiceImpl<TestBatchInsertBySqlServerMapper, TestBatchInsertBySqlServer> implements ITestBatchInsertBySqlServerService {

}
