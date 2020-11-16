package com.rty.springboot.web.controller;

import com.rty.springboot.bean.UserInfo;
import com.rty.springboot.bean.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/say")
    @ResponseBody
    public String helloWorld() {
        return "Hello World";

    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public ResponseResult getUsers() {
        userService.getUser("jack");
        return null;

    }

    @RequestMapping(value = "/insertUser", method = RequestMethod.GET)
    public ResponseResult insertUserInfo() {
        UserInfo userInfo = new UserInfo();
        userService.insertUser(userInfo);
        return null;

    }
}
