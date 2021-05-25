package com.wushi.lade.dao.interfaces.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.wushi.lade.dao.interfaces.UnitOfWork;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author wushi
 * @date 2020/3/25 16:01
 * @description
 */
public class UnitOfWorkImpl implements UnitOfWork {

    private final DruidDataSource dataSource;

    private final DataSourceTransactionManager transactionManager;

    private final TransactionStatus transactionStatus;

    @Override
    public DruidDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public DataSourceTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    @Override
    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public UnitOfWorkImpl(DruidDataSource dataSource) {
        this.dataSource = dataSource;
        this.transactionManager = new DataSourceTransactionManager(this.dataSource);
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        this.transactionStatus = transactionManager.getTransaction(transactionDefinition);
    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {
        this.transactionManager.commit(this.transactionStatus);
    }

    @Override
    public void rollback() {
        this.transactionManager.rollback(this.transactionStatus);
    }

}
