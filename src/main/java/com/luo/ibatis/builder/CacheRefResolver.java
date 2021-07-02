package com.luo.ibatis.builder;

import com.luo.ibatis.cache.Cache;

/**
 * @author ：archer
 * @date ：Created in 2021/6/30 20:54
 * @description： 缓存引用解析器
 */
public class CacheRefResolver {

    private final MapperBuilderAssistant assistant;
    private final String cacheRefNamespace;

    public CacheRefResolver(MapperBuilderAssistant assistant, String cacheRefNamespace) {
        this.assistant = assistant;
        this.cacheRefNamespace = cacheRefNamespace;
    }

    public Cache resolveCacheRef() {
        return assistant.useCacheRef(cacheRefNamespace);
    }
}
