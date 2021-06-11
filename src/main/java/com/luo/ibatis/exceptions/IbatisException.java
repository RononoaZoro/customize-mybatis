package com.luo.ibatis.exceptions;

/**
 * @author ：archer
 * @date ：Created in 2021/6/9 14:50
 * @description：ibatis通用异常
 * @modified By：
 */
@Deprecated
public class IbatisException extends RuntimeException {

    private static final long serialVersionUID = -4519577081974993269L;

    public IbatisException() {
        super();
    }

    public IbatisException(String message) {
        super(message);
    }

    public IbatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public IbatisException(Throwable cause) {
        super(cause);
    }
}
