package com.luo.ibatis.logging;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/10 11:00
 * @description：日志系统异常
 * @modified By：
 */
public class LogException extends PersistenceException {

    private static final long serialVersionUID = 4800164364334152165L;

    public LogException() {
        super();
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogException(Throwable cause) {
        super(cause);
    }
}
