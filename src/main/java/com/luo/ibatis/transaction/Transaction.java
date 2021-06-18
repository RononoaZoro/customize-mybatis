package com.luo.ibatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 20:49
 * @description： 数据库事务
 * Wraps a database connection.
 * Handles the connection lifecycle that comprises(包含): its creation, preparation, commit/rollback and close.
 */
public interface Transaction {

    /**
     * Retrieve inner database connection
     * @return DataBase connection
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * Commit inner database connection.
     * @throws SQLException
     */
    void commit() throws SQLException;

    /**
     * Rollback inner database connection.
     * @throws SQLException
     */
    void rollback() throws SQLException;

    /**
     * Close inner database connection.
     * @throws SQLException
     */
    void close() throws SQLException;

    /**
     * Get transaction timeout if set
     * @throws SQLException
     */
    Integer getTimeout() throws SQLException;
}
