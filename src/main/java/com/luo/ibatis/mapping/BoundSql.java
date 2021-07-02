package com.luo.ibatis.mapping;

import com.luo.ibatis.reflection.MetaObject;
import com.luo.ibatis.reflection.property.PropertyTokenizer;
import com.luo.ibatis.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 10:39
 * @description：
 * An actual SQL String got from an {@link SqlSource} after having processed any dynamic content.
 * The SQL may have SQL placeholders "?" and an list (ordered) of an parameter mappings
 * with the additional information for each parameter (at least the property name of the input object to read
 * the value from).
 * <p>
 * Can also have additional parameters that are created by the dynamic language (for loops, bind...).
 */
public class BoundSql {

    // Mapper配置解析后的sql语句
    private final String sql;
    // Mapper参数映射信息
    private final List<ParameterMapping> parameterMappings;
    // Mapper参数对象
    private final Object parameterObject;
    // 额外参数信息，包括<bind>标签绑定的参数，内置参数
    private final Map<String, Object> additionalParameters;
    // 参数对象对应的MetaObject对象
    private final MetaObject metaParameters;

    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<String, Object>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public boolean hasAdditionalParameter(String name) {
        String paramName = new PropertyTokenizer(name).getName();
        return additionalParameters.containsKey(paramName);
    }

    public void setAdditionalParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    public Object getAdditionalParameter(String name) {
        return metaParameters.getValue(name);
    }
}
