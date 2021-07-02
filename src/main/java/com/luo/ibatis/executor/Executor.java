package com.luo.ibatis.executor;

import com.luo.ibatis.cache.CacheKey;
import com.luo.ibatis.cursor.Cursor;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.reflection.MetaObject;
import com.luo.ibatis.session.ResultHandler;
import com.luo.ibatis.session.RowBounds;
import com.luo.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:00
 * @description： sql 执行器
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    int update(MappedStatement ms, Object parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

    <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

    List<BatchResult> flushStatements() throws SQLException;

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

    boolean isCached(MappedStatement ms, CacheKey key);

    void clearLocalCache();

    void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

    Transaction getTransaction();

    void close(boolean forceRollback);

    boolean isClosed();

    void setExecutorWrapper(Executor executor);
}
