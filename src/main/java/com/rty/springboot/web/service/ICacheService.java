package com.rty.springboot.web.service;

public interface ICacheService {
    /**
     * 清除特定的缓存
     */
    void clearCache(String cacheKey);

    /**
     * 清除所有的缓存
     */
    void clearCache();
}
