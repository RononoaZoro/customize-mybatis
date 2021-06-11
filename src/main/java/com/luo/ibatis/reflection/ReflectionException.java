package com.luo.ibatis.reflection;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 17:48
 * @description：反射异常类
 * @modified By：
 */
public class ReflectionException extends PersistenceException {

    private static final long serialVersionUID = -5371633033429953418L;

    public ReflectionException() {
        super();
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
