package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.bean.UserInfo;
import com.rty.springboot.bean.result.ResponseResult;
import com.rty.springboot.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController extends AbstractContorller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
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

    /**
     * 下载
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResultInfo<?> downLoad(HttpServletRequest request) {
        try {
            ResultInfo resultInfo = createSuccessResult();
            return resultInfo;

        } catch (Exception e) {
            LOGGER.info("download user fail", e);
            return createFailResult("");
        }

    }


}
