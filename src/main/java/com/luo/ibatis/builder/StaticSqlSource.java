package com.luo.ibatis.builder;

import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.ParameterMapping;
import com.luo.ibatis.mapping.SqlSource;
import com.luo.ibatis.session.Configuration;

import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 12:09
 * @description：
 * Mapper解析后的sql内容 描述器
 */
public class StaticSqlSource implements SqlSource {
    // Mapper解析后的sql内容
    private final String sql;
    // 参数映射信息
    private final List<ParameterMapping> parameterMappings;
    private final Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }

}
