package com.luo.ibatis.executor.keygen;

import com.luo.ibatis.executor.Executor;
import com.luo.ibatis.mapping.MappedStatement;

import java.sql.Statement;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:08
 * @description：
 */
public class NoKeyGenerator implements KeyGenerator {

    /**
     * A shared instance.
     * @since 3.4.3
     */
    public static final NoKeyGenerator INSTANCE = new NoKeyGenerator();

    @Override
    public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // Do Nothing
    }

    @Override
    public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
        // Do Nothing
    }

}
