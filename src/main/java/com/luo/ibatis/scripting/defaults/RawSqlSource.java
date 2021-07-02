package com.luo.ibatis.scripting.defaults;

import com.luo.ibatis.builder.SqlSourceBuilder;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.SqlSource;
import com.luo.ibatis.scripting.xmltags.DynamicContext;
import com.luo.ibatis.scripting.xmltags.DynamicSqlSource;
import com.luo.ibatis.scripting.xmltags.SqlNode;
import com.luo.ibatis.session.Configuration;

import java.util.HashMap;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:50
 * @description：
 * Static SqlSource. It is faster than {@link DynamicSqlSource} because mappings are
 * calculated during startup.
 */
public class RawSqlSource implements SqlSource {

    private final SqlSource sqlSource;

    public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }

    public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<String, Object>());
    }

    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration, null);
        rootSqlNode.apply(context);
        return context.getSql();
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }

}
