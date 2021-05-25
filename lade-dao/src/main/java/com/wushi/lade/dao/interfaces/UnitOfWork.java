package com.wushi.lade.dao.interfaces;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * @author wushi
 * @date 2020/3/25 15:59
 * @description
 */
public interface UnitOfWork {

    DruidDataSource getDataSource();

    DataSourceTransactionManager getTransactionManager();

    TransactionStatus getTransactionStatus();

    /**
     * 开始执行事务操作，此步骤可省略
     *
     * @return void
     * @param
     * @author wushi
     * @date 2020/3/25 16:11
     * @description
     */
    void begin();


    void commit();

    void rollback();

}
