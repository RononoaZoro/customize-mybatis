package com.luo.ibatis.logging;

import com.luo.ibatis.logging.commons.JakartaCommonsLoggingImpl;
import com.luo.ibatis.logging.jdk14.Jdk14LoggingImpl;
import com.luo.ibatis.logging.log4j.Log4jImpl;
import com.luo.ibatis.logging.log4j2.Log4j2Impl;
import com.luo.ibatis.logging.nologging.NoLoggingImpl;
import com.luo.ibatis.logging.slf4j.Slf4jImpl;
import com.luo.ibatis.logging.stdout.StdOutImpl;

import java.lang.reflect.Constructor;

/**
 * @author ：archer
 * @date ：Created in 2021/6/10 11:01
 * @description：日志工厂类
 * @modified By：
 */
public class LogFactory {

    /**
     * Marker to be used by logging implementations that support markers
     * 标记支持标记的日志实现
     */
    public static final String MARKER = "MYBATIS";

    private static Constructor<? extends Log> logConstructor;

    static {
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useSlf4jLogging();
            }
        });
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useCommonsLogging();
            }
        });
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useLog4J2Logging();
            }
        });
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useLog4JLogging();
            }
        });
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useJdkLogging();
            }
        });
        tryImplementation(new Runnable() {
            @Override
            public void run() {
                useNoLogging();
            }
        });
    }

    private LogFactory() {
        // disable construction
    }

    public static Log getLog(Class<?> aClass) {
        return getLog(aClass.getName());
    }

    public static Log getLog(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new LogException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
        }
    }

    public static synchronized void useCustomLogging(Class<? extends Log> clazz) {
        setImplementation(clazz);
    }

    public static synchronized void useSlf4jLogging() {
        setImplementation(Slf4jImpl.class);
    }

    public static synchronized void useCommonsLogging() {
        setImplementation(JakartaCommonsLoggingImpl.class);
    }

    public static synchronized void useLog4JLogging() {
        setImplementation(Log4jImpl.class);
    }

    public static synchronized void useLog4J2Logging() {
        setImplementation(Log4j2Impl.class);
    }

    public static synchronized void useJdkLogging() {
        setImplementation(Jdk14LoggingImpl.class);
    }

    public static synchronized void useStdOutLogging() {
        setImplementation(StdOutImpl.class);
    }

    public static synchronized void useNoLogging() {
        setImplementation(NoLoggingImpl.class);
    }

    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            try {
                runnable.run();
            } catch (Throwable t) {
                // ignore
            }
        }
    }

    private static void setImplementation(Class<? extends Log> implClass) {
        try {
            // 获取日志实现类的Constructor对象
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            // 根据日志实现类创建Log实例
            Log log = candidate.newInstance(LogFactory.class.getName());
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + implClass + "' adapter.");
            }
            // 记录当前使用的日志实现类的Constructor对象
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t, t);
        }
    }
}
