package com.luo.ibatis.executor.loader;

import com.luo.ibatis.reflection.factory.ObjectFactory;
import com.luo.ibatis.session.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 14:38
 * @description：
 */
public interface ProxyFactory {

    void setProperties(Properties properties);

    Object createProxy(Object target, ResultLoaderMap lazyLoader, Configuration configuration, ObjectFactory objectFactory, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);


}
