package com.luo.ibatis.reflection;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 17:42
 * @description：反射工厂接口
 * @modified By：
 */
public interface ReflectorFactory {

    boolean isClassCacheEnabled();

    void setClassCacheEnabled(boolean classCacheEnabled);

    Reflector findForClass(Class<?> type);
}
