package com.luo.ibatis.reflection.wrapper;

import com.luo.ibatis.reflection.MetaObject;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 15:47
 * @description：
 */
public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);

    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
}
