package com.luo.ibatis.plugin;

import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:12
 * @description：
 */
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);

    void setProperties(Properties properties);
}
