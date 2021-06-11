package com.luo.ibatis.exceptions;

/**
 * @author ：archer
 * @date ：Created in 2021/6/9 14:52
 * @description：持久化异常
 * @modified By：
 */
public class PersistenceException extends IbatisException {

    private static final long serialVersionUID = 8259334790430382398L;

    public PersistenceException() {
        super();
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
