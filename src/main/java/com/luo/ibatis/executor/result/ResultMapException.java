package com.luo.ibatis.executor.result;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/11 18:07
 * @description：结果异常
 * @modified By：
 */
public class ResultMapException extends PersistenceException {

    private static final long serialVersionUID = -1193182307614731846L;

    public ResultMapException() {
    }

    public ResultMapException(String message) {
        super(message);
    }

    public ResultMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultMapException(Throwable cause) {
        super(cause);
    }
}
