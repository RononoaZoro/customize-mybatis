package com.luo.ibatis.reflection.wrapper;

import com.luo.ibatis.reflection.MetaObject;
import com.luo.ibatis.reflection.ObjectWrapper;
import com.luo.ibatis.reflection.ObjectWrapperFactory;
import com.luo.ibatis.reflection.ReflectionException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/15 15:59
 * @description：默认对象装饰工厂类
 * @modified By：
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }

}