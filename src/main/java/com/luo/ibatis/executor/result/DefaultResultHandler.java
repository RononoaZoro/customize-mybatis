package com.luo.ibatis.executor.result;

import com.luo.ibatis.reflection.factory.ObjectFactory;
import com.luo.ibatis.session.ResultContext;
import com.luo.ibatis.session.ResultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:38
 * @description：
 */
public class DefaultResultHandler implements ResultHandler<Object> {

    private final List<Object> list;

    public DefaultResultHandler() {
        list = new ArrayList<Object>();
    }

    @SuppressWarnings("unchecked")
    public DefaultResultHandler(ObjectFactory objectFactory) {
        list = objectFactory.create(List.class);
    }

    @Override
    public void handleResult(ResultContext<? extends Object> context) {
        list.add(context.getResultObject());
    }

    public List<Object> getResultList() {
        return list;
    }

}
