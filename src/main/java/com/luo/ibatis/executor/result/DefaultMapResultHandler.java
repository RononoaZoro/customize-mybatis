package com.luo.ibatis.executor.result;

import com.luo.ibatis.reflection.MetaObject;
import com.luo.ibatis.reflection.ReflectorFactory;
import com.luo.ibatis.reflection.factory.ObjectFactory;
import com.luo.ibatis.reflection.wrapper.ObjectWrapperFactory;
import com.luo.ibatis.session.ResultContext;
import com.luo.ibatis.session.ResultHandler;

import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/6 9:59
 * @description：
 */
public class DefaultMapResultHandler <K, V> implements ResultHandler<V> {

    private final Map<K, V> mappedResults;
    private final String mapKey;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;
    private final ReflectorFactory reflectorFactory;

    @SuppressWarnings("unchecked")
    public DefaultMapResultHandler(String mapKey, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        this.mappedResults = objectFactory.create(Map.class);
        this.mapKey = mapKey;
    }

    @Override
    public void handleResult(ResultContext<? extends V> context) {
        final V value = context.getResultObject();
        final MetaObject mo = MetaObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
        // TODO is that assignment always true?
        final K key = (K) mo.getValue(mapKey);
        mappedResults.put(key, value);
    }

    public Map<K, V> getMappedResults() {
        return mappedResults;
    }
}
