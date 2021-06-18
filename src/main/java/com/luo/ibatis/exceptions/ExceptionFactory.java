package com.luo.ibatis.exceptions;

import com.luo.ibatis.executor.ErrorContext;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 21:10
 * @description：
 */
public class ExceptionFactory {

    private ExceptionFactory() {
        // Prevent Instantiation
    }

    public static RuntimeException wrapException(String message, Exception e) {
        return new PersistenceException(ErrorContext.instance().message(message).cause(e).toString(), e);
    }
}
