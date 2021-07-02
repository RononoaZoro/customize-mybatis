package com.luo.ibatis.session;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:31
 * @description：
 */
public interface ResultContext<T> {

    T getResultObject();

    int getResultCount();

    boolean isStopped();

    void stop();
}
