package com.luo.ibatis.logging;

/**
 * @author ：archer
 * @date ：Created in 2021/6/10 11:00
 * @description：日志系统超类
 * @modified By：
 */
public interface Log {

    boolean isDebugEnabled();

    boolean isTraceEnabled();

    void error(String s, Throwable e);

    void error(String s);

    void debug(String s);

    void trace(String s);

    void warn(String s);
}
