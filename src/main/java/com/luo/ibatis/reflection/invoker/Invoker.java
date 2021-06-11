package com.luo.ibatis.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 17:46
 * @description：反射方法调用接口
 * @modified By：
 */
public interface Invoker {

    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();
}
