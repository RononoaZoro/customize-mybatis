package com.luo.ibatis.reflection;

import com.luo.ibatis.reflection.factory.ObjectFactory;
import com.luo.ibatis.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/6/15 15:57
 * @description： 对象装饰器
 * @modified By：
 */
public interface ObjectWrapper {


    Object get(PropertyTokenizer prop);

    void set(PropertyTokenizer prop, Object value);

    String findProperty(String name, boolean useCamelCaseMapping);

    String[] getGetterNames();

    String[] getSetterNames();

    Class<?> getSetterType(String name);

    Class<?> getGetterType(String name);

    boolean hasSetter(String name);

    boolean hasGetter(String name);

    MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);

    boolean isCollection();

    void add(Object element);

    <E> void addAll(List<E> element);
}
