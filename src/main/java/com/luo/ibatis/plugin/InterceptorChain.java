package com.luo.ibatis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:12
 * @description：
 */
public class InterceptorChain {

    // 通过List对象维护所有拦截器实例
    private final List<Interceptor> interceptors = new ArrayList<Interceptor>();

    // 调用所有拦截器对象的plugin（）方法执行拦截逻辑
    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(interceptors);
    }
}
