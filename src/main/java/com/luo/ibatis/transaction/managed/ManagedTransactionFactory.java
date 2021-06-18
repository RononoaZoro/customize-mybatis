package com.luo.ibatis.transaction.managed;

import com.luo.ibatis.session.TransactionIsolationLevel;
import com.luo.ibatis.transaction.Transaction;
import com.luo.ibatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 21:02
 * @description：
 */
public class ManagedTransactionFactory implements TransactionFactory {

    private boolean closeConnection = true;

    @Override
    public void setProperties(Properties props) {
        if (props != null) {
            String closeConnectionProperty = props.getProperty("closeConnection");
            if (closeConnectionProperty != null) {
                closeConnection = Boolean.valueOf(closeConnectionProperty);
            }
        }
    }

    @Override
    public Transaction newTransaction(Connection conn) {
        return new ManagedTransaction(conn, closeConnection);
    }

    @Override
    public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
        // Silently ignores autocommit and isolation level, as managed transactions are entirely
        // controlled by an external manager.  It's silently ignored so that
        // code remains portable between managed and unmanaged configurations.
        return new ManagedTransaction(ds, level, closeConnection);
    }
}
