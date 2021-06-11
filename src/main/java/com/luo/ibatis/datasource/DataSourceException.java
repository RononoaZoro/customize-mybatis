package com.luo.ibatis.datasource;

import com.luo.ibatis.exceptions.PersistenceException;

/**
 * @author ：archer
 * @date ：Created in 2021/6/9 16:41
 * @description：数据源异常
 * @modified By：
 */
public class DataSourceException extends PersistenceException {


    private static final long serialVersionUID = -1621782092541862547L;

    public DataSourceException() {
        super();
    }

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }
}
