package com.luo.ibatis.jdbc;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/10 11:48
 * @description：sql执行异常
 * @modified By：
 */
public class RuntimeSqlException extends PersistenceException {

    private static final long serialVersionUID = 9108324819918365237L;

    public RuntimeSqlException() {
        super();
    }

    public RuntimeSqlException(String message) {
        super(message);
    }

    public RuntimeSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeSqlException(Throwable cause) {
        super(cause);
    }
}
