package com.luo.ibatis.executor.keygen;

import com.luo.ibatis.executor.Executor;
import com.luo.ibatis.mapping.MappedStatement;

import java.sql.Statement;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 10:59
 * @description：
 */
public interface KeyGenerator {

    void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

    void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter);
}
