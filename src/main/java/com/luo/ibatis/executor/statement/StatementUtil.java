package com.luo.ibatis.executor.statement;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:18
 * @description：
 */
public class StatementUtil {

    private StatementUtil() {
        // NOP
    }

    /**
     * Apply a transaction timeout.
     * <p>
     * Update a query timeout to apply a transaction timeout.
     * </p>
     * @param statement a target statement
     * @param queryTimeout a query timeout
     * @param transactionTimeout a transaction timeout
     * @throws SQLException if a database access error occurs, this method is called on a closed <code>Statement</code>
     */
    public static void applyTransactionTimeout(Statement statement, Integer queryTimeout, Integer transactionTimeout) throws SQLException {
        if (transactionTimeout == null){
            return;
        }
        Integer timeToLiveOfQuery = null;
        if (queryTimeout == null || queryTimeout == 0) {
            timeToLiveOfQuery = transactionTimeout;
        } else if (transactionTimeout < queryTimeout) {
            timeToLiveOfQuery = transactionTimeout;
        }
        if (timeToLiveOfQuery != null) {
            statement.setQueryTimeout(timeToLiveOfQuery);
        }
    }
}
