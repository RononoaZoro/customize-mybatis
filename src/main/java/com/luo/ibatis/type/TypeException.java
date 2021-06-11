package com.luo.ibatis.type;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 18:05
 * @description：类型异常
 * @modified By：
 */
public class TypeException extends PersistenceException {

    private static final long serialVersionUID = -8467480244059612894L;

    public TypeException() {
        super();
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }
}
