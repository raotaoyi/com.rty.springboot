package com.rty.springboot.web.service.impl;


import com.rty.springboot.bean.UserInfo;
import com.rty.springboot.web.mapper.UserMapper;
import com.rty.springboot.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;


    @Override
    public UserInfo getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public void insertUser(UserInfo user) {

    }
}
