package com.wushi.lade.tests.service.mysql.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.mysql.TestAutoIncrementByMysql;
import com.wushi.lade.tests.mapper.mysql.TestAutoIncrementByMysqlMapper;
import com.wushi.lade.tests.service.mysql.ITestAutoIncrementByMysqlService;
import org.springframework.stereotype.Service;

/**
 * TestAutoIncrement 服务实现类
 *
 * @author wushi
 * @since 2020-01-08 13:52
 */
@Service
@DS("mysql")
public class TestAutoIncrementByMysqlServiceImpl extends ServiceImpl<TestAutoIncrementByMysqlMapper, TestAutoIncrementByMysql> implements ITestAutoIncrementByMysqlService {

}
