package com.luo.ibatis.transaction;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/17 20:49
 * @description：事务异常
 */
public class TransactionException extends PersistenceException {

    private static final long serialVersionUID = -433589569461084605L;

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

}
