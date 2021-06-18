package com.luo.ibatis.transaction;

import com.luo.ibatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 20:50
 * @description：事务工厂接口
 */
public interface TransactionFactory {

    /**
     * Sets transaction factory custom properties.
     * @param props
     */
    void setProperties(Properties props);

    /**
     * Creates a {@link Transaction} out of an existing connection.
     * @param conn Existing database connection
     * @return Transaction
     * @since 3.1.0
     */
    Transaction newTransaction(Connection conn);

    /**
     * Creates a {@link Transaction} out of a datasource.
     * @param dataSource DataSource to take the connection from
     * @param level Desired isolation level
     * @param autoCommit Desired autocommit
     * @return Transaction
     * @since 3.1.0
     */
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
