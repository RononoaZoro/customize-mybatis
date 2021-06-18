package com.luo.ibatis.transaction.jdbc;

import com.luo.ibatis.session.TransactionIsolationLevel;
import com.luo.ibatis.transaction.Transaction;
import com.luo.ibatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 21:24
 * @description：
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public void setProperties(Properties props) {
    }

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(ds, level, autoCommit);
    }
}