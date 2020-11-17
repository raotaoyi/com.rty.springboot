package com.rty.springboot.test.dao;

import com.rty.springboot.test.ApplicationTest;
import com.rty.springboot.web.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapperTest extends ApplicationTest {

    @Autowired
    private UserMapper userDao;

    @Test
    public void testUser(){
        System.out.println(userDao.getUser("jack"));
    }
}
