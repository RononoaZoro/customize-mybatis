package com.luo.ibatis.executor.statement;

import com.luo.ibatis.cursor.Cursor;
import com.luo.ibatis.executor.Executor;
import com.luo.ibatis.executor.ExecutorException;
import com.luo.ibatis.executor.keygen.KeyGenerator;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.mapping.ParameterMapping;
import com.luo.ibatis.mapping.ParameterMode;
import com.luo.ibatis.session.ResultHandler;
import com.luo.ibatis.session.RowBounds;
import com.luo.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/8 17:58
 * @description：
 */
public class CallableStatementHandler extends BaseStatementHandler {

    public CallableStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        super(executor, mappedStatement, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        CallableStatement cs = (CallableStatement) statement;
        cs.execute();
        int rows = cs.getUpdateCount();
        Object parameterObject = boundSql.getParameterObject();
        KeyGenerator keyGenerator = mappedStatement.getKeyGenerator();
        keyGenerator.processAfter(executor, mappedStatement, cs, parameterObject);
        resultSetHandler.handleOutputParameters(cs);
        return rows;
    }

    @Override
    public void batch(Statement statement) throws SQLException {
        CallableStatement cs = (CallableStatement) statement;
        cs.addBatch();
    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        CallableStatement cs = (CallableStatement) statement;
        cs.execute();
        List<E> resultList = resultSetHandler.<E>handleResultSets(cs);
        resultSetHandler.handleOutputParameters(cs);
        return resultList;
    }

    @Override
    public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
        CallableStatement cs = (CallableStatement) statement;
        cs.execute();
        Cursor<E> resultList = resultSetHandler.<E>handleCursorResultSets(cs);
        resultSetHandler.handleOutputParameters(cs);
        return resultList;
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        String sql = boundSql.getSql();
        if (mappedStatement.getResultSetType() != null) {
            return connection.prepareCall(sql, mappedStatement.getResultSetType().getValue(), ResultSet.CONCUR_READ_ONLY);
        } else {
            return connection.prepareCall(sql);
        }
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        registerOutputParameters((CallableStatement) statement);
        parameterHandler.setParameters((CallableStatement) statement);
    }

    private void registerOutputParameters(CallableStatement cs) throws SQLException {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0, n = parameterMappings.size(); i < n; i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            if (parameterMapping.getMode() == ParameterMode.OUT || parameterMapping.getMode() == ParameterMode.INOUT) {
                if (null == parameterMapping.getJdbcType()) {
                    throw new ExecutorException("The JDBC Type must be specified for output parameter.  Parameter: " + parameterMapping.getProperty());
                } else {
                    if (parameterMapping.getNumericScale() != null && (parameterMapping.getJdbcType() == JdbcType.NUMERIC || parameterMapping.getJdbcType() == JdbcType.DECIMAL)) {
                        cs.registerOutParameter(i + 1, parameterMapping.getJdbcType().TYPE_CODE, parameterMapping.getNumericScale());
                    } else {
                        if (parameterMapping.getJdbcTypeName() == null) {
                            cs.registerOutParameter(i + 1, parameterMapping.getJdbcType().TYPE_CODE);
                        } else {
                            cs.registerOutParameter(i + 1, parameterMapping.getJdbcType().TYPE_CODE, parameterMapping.getJdbcTypeName());
                        }
                    }
                }
            }
        }
    }

}
