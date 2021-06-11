package com.luo.ibatis.builder;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/9 14:49
 * @description：构造异常
 * @modified By：
 */
public class BuilderException extends PersistenceException {

    private static final long serialVersionUID = -8444801809640278008L;

    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}
