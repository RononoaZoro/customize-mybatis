package com.luo.ibatis.reflection.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 17:50
 * @description：获取属性值
 * @modified By：
 */
public class GetFieldInvoker implements Invoker {

    private final Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return field.get(target);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }
}
