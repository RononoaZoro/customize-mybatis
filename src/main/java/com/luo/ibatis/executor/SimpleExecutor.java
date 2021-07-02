package com.luo.ibatis.executor;

import com.luo.ibatis.cursor.Cursor;
import com.luo.ibatis.executor.statement.StatementHandler;
import com.luo.ibatis.logging.Log;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.session.Configuration;
import com.luo.ibatis.session.ResultHandler;
import com.luo.ibatis.session.RowBounds;
import com.luo.ibatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:20
 * @description：
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    public int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
        Statement stmt = null;
        try {
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, RowBounds.DEFAULT, null, null);
            stmt = prepareStatement(handler, ms.getStatementLog());
            return handler.update(stmt);
        } finally {
            closeStatement(stmt);
        }
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        Statement stmt = null;
        try {
            Configuration configuration = ms.getConfiguration();
            // 获取StatementHandler对象
            StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);
            // 调用prepareStatement（）方法,创建Statement对象，并进行设置参数等操作
            stmt = prepareStatement(handler, ms.getStatementLog());
            // 调用StatementHandler对象的query（）方法执行查询操作
            return handler.<E>query(stmt, resultHandler);
        } finally {
            closeStatement(stmt);
        }
    }

    @Override
    protected <E> Cursor<E> doQueryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds, BoundSql boundSql) throws SQLException {
        Configuration configuration = ms.getConfiguration();
        StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, null, boundSql);
        Statement stmt = prepareStatement(handler, ms.getStatementLog());
        return handler.<E>queryCursor(stmt);
    }

    @Override
    public List<BatchResult> doFlushStatements(boolean isRollback) throws SQLException {
        return Collections.emptyList();
    }

    private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
        Statement stmt;
        // 获取JDBC中的Connection对象
        Connection connection = getConnection(statementLog);
        // 调用StatementHandler的prepare（）方法创建Statement对象
        stmt = handler.prepare(connection, transaction.getTimeout());
        // 调用StatementHandler对象的parameterize（）方法设置参数
        handler.parameterize(stmt);
        return stmt;
    }

}
