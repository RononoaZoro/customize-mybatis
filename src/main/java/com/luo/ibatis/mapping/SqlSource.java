package com.luo.ibatis.mapping;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 10:39
 * @description：
 * Represents the content of a mapped statement read from an XML file or an annotation.
 * It creates the SQL that will be passed to the database out of the input parameter received from the user.
 */
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);
}
