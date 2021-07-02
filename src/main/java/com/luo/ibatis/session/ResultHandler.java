package com.luo.ibatis.session;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:22
 * @description：
 */
public interface ResultHandler<T> {

    void handleResult(ResultContext<? extends T> resultContext);
}
