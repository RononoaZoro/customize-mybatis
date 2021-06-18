package com.luo.ibatis.reflection;

/**
 * @author ：archer
 * @date ：Created in 2021/6/15 15:56
 * @description：对象装饰工厂
 * @modified By：
 */
public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
