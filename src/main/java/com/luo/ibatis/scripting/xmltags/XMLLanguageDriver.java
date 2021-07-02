package com.luo.ibatis.scripting.xmltags;

import com.luo.ibatis.builder.xml.XMLMapperEntityResolver;
import com.luo.ibatis.executor.parameter.ParameterHandler;
import com.luo.ibatis.mapping.BoundSql;
import com.luo.ibatis.mapping.MappedStatement;
import com.luo.ibatis.mapping.SqlSource;
import com.luo.ibatis.parsing.PropertyParser;
import com.luo.ibatis.parsing.XNode;
import com.luo.ibatis.parsing.XPathParser;
import com.luo.ibatis.scripting.LanguageDriver;
import com.luo.ibatis.scripting.defaults.DefaultParameterHandler;
import com.luo.ibatis.scripting.defaults.RawSqlSource;
import com.luo.ibatis.session.Configuration;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:47
 * @description：
 */
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }
    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        // 该方法用于解析XML文件中配置的SQL信息
        // 创建XMLScriptBuilder对象
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        // 调用 XMLScriptBuilder对象parseScriptNode（）方法解析SQL资源
        return builder.parseScriptNode();
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        // 该方法用于解析Java注解中配置的SQL信息
        // 字符串以<script>标签开头，则以XML方式解析
        if (script.startsWith("<script>")) {
            XPathParser parser = new XPathParser(script, false, configuration.getVariables(), new XMLMapperEntityResolver());
            return createSqlSource(configuration, parser.evalNode("/script"), parameterType);
        } else {
            // 解析SQL配置中的全局变量
            script = PropertyParser.parse(script, configuration.getVariables());
            TextSqlNode textSqlNode = new TextSqlNode(script);
            // 如果SQL中是否仍包含${}参数占位符，则返回DynamicSqlSource实例，否则返回RawSqlSource
            if (textSqlNode.isDynamic()) {
                return new DynamicSqlSource(configuration, textSqlNode);
            } else {
                return new RawSqlSource(configuration, script, parameterType);
            }
        }
    }

}
