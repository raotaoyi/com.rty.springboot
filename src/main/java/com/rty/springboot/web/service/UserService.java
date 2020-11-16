package com.rty.springboot.web.service;


import com.rty.springboot.bean.UserInfo;

public interface UserService {

    UserInfo getUser(String username);

    void insertUser(UserInfo user);
}