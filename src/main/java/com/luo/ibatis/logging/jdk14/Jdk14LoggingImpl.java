package com.luo.ibatis.logging.jdk14;

import com.luo.ibatis.logging.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ：archer
 * @date ：Created in 2021/6/10 12:02
 * @description：jdk14日志实现类
 * @modified By：
 */
public class Jdk14LoggingImpl implements Log {
    private final Logger log;

    public Jdk14LoggingImpl(String clazz) {
        log = Logger.getLogger(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isLoggable(Level.FINE);
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isLoggable(Level.FINER);
    }

    @Override
    public void error(String s, Throwable e) {
        log.log(Level.SEVERE, s, e);
    }

    @Override
    public void error(String s) {
        log.log(Level.SEVERE, s);
    }

    @Override
    public void debug(String s) {
        log.log(Level.FINE, s);
    }

    @Override
    public void trace(String s) {
        log.log(Level.FINER, s);
    }

    @Override
    public void warn(String s) {
        log.log(Level.WARNING, s);
    }

}
