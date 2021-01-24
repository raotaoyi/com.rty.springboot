package com.rty.springboot.web.service.impl;

import com.rty.springboot.web.service.ICacheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 缓存清理
 *
 * @author rty
 * @since 2021-01-24
 */
@Service
public class CacheServiceImpl implements ICacheService {
    private static final Log LOGGER = LogFactory.getLog(CacheServiceImpl.class);

    /**
     * 　beforeInvocation 是 @CacheEvict 中特有的一个属性
     */
    @Override
    @CacheEvict(value = "rty_cache", key = "#cacheKey")
    public void clearCache(String cacheKey) {
        LOGGER.info("clear cache");
    }

    @Override
    @CacheEvict(value = "rty_cache",allEntries = true)
    public void clearCache() {
        LOGGER.info("clear all cache");
    }
}
