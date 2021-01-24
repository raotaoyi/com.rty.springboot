package com.rty.springboot.web.service.impl;

import com.rty.springboot.web.service.ICacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements ICacheService {
    /**
     * 　beforeInvocation 是 @CacheEvict 中特有的一个属性
     */
    @Override
    @CacheEvict(value = "rty_cache", key = "#cacheKey")
    public void clearCache(String cacheKey) {

    }
}
