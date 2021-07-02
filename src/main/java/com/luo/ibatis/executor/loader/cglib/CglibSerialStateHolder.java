package com.luo.ibatis.executor.loader.cglib;

import com.luo.ibatis.executor.loader.AbstractSerialStateHolder;
import com.luo.ibatis.executor.loader.ResultLoaderMap;
import com.luo.ibatis.reflection.factory.ObjectFactory;

import java.util.List;
import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 15:01
 * @description：
 */
public class CglibSerialStateHolder extends AbstractSerialStateHolder {

    private static final long serialVersionUID = 8940388717901644661L;

    public CglibSerialStateHolder() {
    }

    public CglibSerialStateHolder(
            final Object userBean,
            final Map<String, ResultLoaderMap.LoadPair> unloadedProperties,
            final ObjectFactory objectFactory,
            List<Class<?>> constructorArgTypes,
            List<Object> constructorArgs) {
        super(userBean, unloadedProperties, objectFactory, constructorArgTypes, constructorArgs);
    }

    @Override
    protected Object createDeserializationProxy(Object target, Map<String, ResultLoaderMap.LoadPair> unloadedProperties, ObjectFactory objectFactory,
                                                List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        return new CglibProxyFactory().createDeserializationProxy(target, unloadedProperties, objectFactory, constructorArgTypes, constructorArgs);
    }
}
