package com.luo.ibatis.executor.statement;

import com.luo.ibatis.cursor.Cursor;
import com.luo.ibatis.executor.parameter.ParameterHandler;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:21
 * @description：
 */
public interface StatementHandler {

    Statement prepare(Connection connection, Integer transactionTimeout)
            throws SQLException;

    void parameterize(Statement statement)
            throws SQLException;

    void batch(Statement statement)
            throws SQLException;

    int update(Statement statement)
            throws SQLException;

    <E> List<E> query(Statement statement, ResultHandler resultHandler)
            throws SQLException;

    <E> Cursor<E> queryCursor(Statement statement)
            throws SQLException;

    BoundSql getBoundSql();

    ParameterHandler getParameterHandler();
}
