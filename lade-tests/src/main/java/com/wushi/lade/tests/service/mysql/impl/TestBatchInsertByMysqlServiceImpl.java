package com.wushi.lade.tests.service.mysql.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushi.lade.tests.entity.mysql.TestBatchInsertByMysql;
import com.wushi.lade.tests.mapper.mysql.TestBatchInsertByMysqlMapper;
import com.wushi.lade.tests.service.mysql.ITestBatchInsertByMysqlService;
import org.springframework.stereotype.Service;

/**
 * TestBatchInsert 服务实现类
 *
 * @author wushi
 * @since 2020-01-08 13:52
 */
@Service
@DS("mysql")
public class TestBatchInsertByMysqlServiceImpl extends ServiceImpl<TestBatchInsertByMysqlMapper, TestBatchInsertByMysql> implements ITestBatchInsertByMysqlService {

}
