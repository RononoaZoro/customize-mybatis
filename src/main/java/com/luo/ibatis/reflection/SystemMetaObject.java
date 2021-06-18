package com.luo.ibatis.reflection;

import com.luo.ibatis.reflection.factory.DefaultObjectFactory;
import com.luo.ibatis.reflection.factory.ObjectFactory;
import com.luo.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

/**
 * @author ：archer
 * @date ：Created in 2021/6/15 15:54
 * @description： 系统元对象信息
 * @modified By：
 */
public class SystemMetaObject {

    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

    private SystemMetaObject() {
        // Prevent Instantiation of Static Class
    }

    private static class NullObject {
    }

    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
    }
}
