package com.luo.ibatis.scripting.xmltags;

import com.luo.ibatis.builder.SqlSourceBuilder;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.SqlSource;
import com.luo.ibatis.session.Configuration;

import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:51
 * @description：
 */
public class DynamicSqlSource implements SqlSource {

    private final Configuration configuration;
    private final SqlNode rootSqlNode;

    public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
        this.configuration = configuration;
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        // 通过参数对象，创建动态SQL上下文对象
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        // 以DynamicContext对象作为参数调用SqlNode的apply（）方法
        rootSqlNode.apply(context);
        // 创建SqlSourceBuilder对象
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        // 调用DynamicContext的getSql()方法获取动态SQL解析后的SQL内容，
        // 然后调用SqlSourceBuilder的parse（）方法对SQL内容做进一步处理，生成StaticSqlSource对象
        SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
        // 调用StaticSqlSource对象的getBoundSql（）方法，获得BoundSql实例
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        // 將<bind>标签绑定的参数添加到BoundSql对象中
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return boundSql;
    }

}
