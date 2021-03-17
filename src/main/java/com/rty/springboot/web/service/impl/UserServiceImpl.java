package com.rty.springboot.web.service.impl;


import com.rty.springboot.bean.UserInfo;
import com.rty.springboot.web.mapper.mc.UserMapper;
import com.rty.springboot.web.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Log LOGGER = LogFactory.getLog(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;


    /**
     * Cacheable的用法:有相应的key的缓存，下一次访问,就直接在缓存中获取
     */
    @Override
    @Cacheable(value = "rty_cache", key = "#root.methodName")
    public UserInfo getUser(String username) {
        LOGGER.info("from db get user");
        return userMapper.getUser(username);
    }

    @Override
    public void insertUser(UserInfo user) {

    }
}
