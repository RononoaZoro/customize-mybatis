package com.luo.ibatis.cache;

import com.luo.ibatis.cache.decorators.TransactionalCache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:43
 * @description：
 */
public class TransactionalCacheManager {
    // 通过HashMap对象维护二级缓存对应的TransactionalCache实例
    private final Map<Cache, TransactionalCache> transactionalCaches = new HashMap<Cache, TransactionalCache>();

    public void clear(Cache cache) {
        getTransactionalCache(cache).clear();
    }

    public Object getObject(Cache cache, CacheKey key) {
        // 获取二级缓存对应的TransactionalCache对象，然后根据缓存Key获取缓存对象
        return getTransactionalCache(cache).getObject(key);
    }

    public void putObject(Cache cache, CacheKey key, Object value) {
        getTransactionalCache(cache).putObject(key, value);
    }

    public void commit() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.commit();
        }
    }

    public void rollback() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.rollback();
        }
    }

    private TransactionalCache getTransactionalCache(Cache cache) {
        // 获取二级缓存对应的TransactionalCache对象
        TransactionalCache txCache = transactionalCaches.get(cache);
        if (txCache == null) {
            // 如果获取不到则创建，然后添加到Map中
            txCache = new TransactionalCache(cache);
            transactionalCaches.put(cache, txCache);
        }
        return txCache;
    }

}

