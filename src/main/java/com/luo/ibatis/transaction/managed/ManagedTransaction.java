package com.luo.ibatis.transaction.managed;

import com.luo.ibatis.logging.Log;
import com.luo.ibatis.logging.LogFactory;
import com.luo.ibatis.session.TransactionIsolationLevel;
import com.luo.ibatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 21:01
 * @description：
 * {@link Transaction} that lets the container manage the full lifecycle of the transaction.
 * Delays connection retrieval until getConnection() is called.
 * Ignores all commit or rollback requests.
 * By default, it closes the connection but can be configured not to do it.
 */
public class ManagedTransaction implements Transaction {

    private static final Log log = LogFactory.getLog(ManagedTransaction.class);

    private DataSource dataSource;
    private TransactionIsolationLevel level;
    private Connection connection;
    private final boolean closeConnection;

    public ManagedTransaction(Connection connection, boolean closeConnection) {
        this.connection = connection;
        this.closeConnection = closeConnection;
    }

    public ManagedTransaction(DataSource ds, TransactionIsolationLevel level, boolean closeConnection) {
        this.dataSource = ds;
        this.level = level;
        this.closeConnection = closeConnection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            openConnection();
        }
        return this.connection;
    }

    @Override
    public void commit() throws SQLException {
        // Does nothing
    }

    @Override
    public void rollback() throws SQLException {
        // Does nothing
    }

    @Override
    public void close() throws SQLException {
        if (this.closeConnection && this.connection != null) {
            if (log.isDebugEnabled()) {
                log.debug("Closing JDBC Connection [" + this.connection + "]");
            }
            this.connection.close();
        }
    }

    protected void openConnection() throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("Opening JDBC Connection");
        }
        this.connection = this.dataSource.getConnection();
        if (this.level != null) {
            this.connection.setTransactionIsolation(this.level.getLevel());
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return null;
    }

}