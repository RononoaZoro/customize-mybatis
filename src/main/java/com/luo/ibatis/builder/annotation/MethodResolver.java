package com.luo.ibatis.builder.annotation;

import java.lang.reflect.Method;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 10:29
 * @description：
 */
public class MethodResolver {

    private final MapperAnnotationBuilder annotationBuilder;
    private final Method method;

    public MethodResolver(MapperAnnotationBuilder annotationBuilder, Method method) {
        this.annotationBuilder = annotationBuilder;
        this.method = method;
    }

    public void resolve() {
        annotationBuilder.parseStatement(method);
    }
}
