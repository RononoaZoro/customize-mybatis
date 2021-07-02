package com.luo.ibatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:13
 * @description：
 */
public class Invocation {

    // 目标对象，即ParameterHandler、ResultSetHandler、StatementHandler或者Executor实例
    private final Object target;
    // 目标方法，即拦截的方法
    private final Method method;
    // 目标方法参数
    private final Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    /**
     * 执行目标方法
     * @return 目标方法执行结果
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }

}
