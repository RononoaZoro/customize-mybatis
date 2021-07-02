package com.luo.ibatis.executor.statement;

import com.luo.ibatis.cursor.Cursor;
import com.luo.ibatis.executor.Executor;
import com.luo.ibatis.executor.ExecutorException;
import com.luo.ibatis.executor.parameter.ParameterHandler;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.session.ResultHandler;
import com.luo.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:25
 * @description：
 */
public class RoutingStatementHandler implements StatementHandler {

    private final StatementHandler delegate;

    public RoutingStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {

        switch (ms.getStatementType()) {
            case STATEMENT:
                delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                break;
//            case PREPARED:
//                delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//                break;
//            case CALLABLE:
//                delegate = new CallableStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//                break;
            default:
                throw new ExecutorException("Unknown statement type: " + ms.getStatementType());
        }

    }

    @Override
    public Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException {
        return delegate.prepare(connection, transactionTimeout);
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        delegate.parameterize(statement);
    }

    @Override
    public void batch(Statement statement) throws SQLException {
        delegate.batch(statement);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        return delegate.update(statement);
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        return delegate.<E>query(statement, resultHandler);
    }

    @Override
    public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
        return delegate.queryCursor(statement);
    }

    @Override
    public BoundSql getBoundSql() {
        return delegate.getBoundSql();
    }

    @Override
    public ParameterHandler getParameterHandler() {
        return delegate.getParameterHandler();
    }
}
