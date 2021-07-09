package com.luo.ibatis.executor;

import com.luo.ibatis.mapping.MappedStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/6/30 17:39
 * @description：
 */
public class BatchResult {

    private final MappedStatement mappedStatement;
    private final String sql;
    private final List<Object> parameterObjects;

    private int[] updateCounts;

    public BatchResult(MappedStatement mappedStatement, String sql) {
        super();
        this.mappedStatement = mappedStatement;
        this.sql = sql;
        this.parameterObjects = new ArrayList<Object>();
    }

    public BatchResult(MappedStatement mappedStatement, String sql, Object parameterObject) {
        this(mappedStatement, sql);
        addParameterObject(parameterObject);
    }

    public MappedStatement getMappedStatement() {
        return mappedStatement;
    }

    public String getSql() {
        return sql;
    }

    @Deprecated
    public Object getParameterObject() {
        return parameterObjects.get(0);
    }

    public List<Object> getParameterObjects() {
        return parameterObjects;
    }

    public int[] getUpdateCounts() {
        return updateCounts;
    }

    public void setUpdateCounts(int[] updateCounts) {
        this.updateCounts = updateCounts;
    }

    public void addParameterObject(Object parameterObject) {
        this.parameterObjects.add(parameterObject);
    }
}
