package com.wushi.lade.tests.service.sqlserver.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.sqlserver.TestAutoIncrementBySqlServer;
import com.wushi.lade.tests.mapper.sqlserver.TestAutoIncrementBySqlServerMapper;
import com.wushi.lade.tests.service.sqlserver.ITestAutoIncrementBySqlServerService;
import org.springframework.stereotype.Service;

/**
 * TestAutoIncrement 服务实现类
 *
 * @author wushi
 * @since 2020-01-08 13:52
 */
@Service
@DS("sqlserver")
public class TestAutoIncrementBySqlServerServiceImpl extends ServiceImpl<TestAutoIncrementBySqlServerMapper, TestAutoIncrementBySqlServer> implements ITestAutoIncrementBySqlServerService {

}
