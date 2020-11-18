package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.bean.UserInfo;
import com.rty.springboot.bean.result.ResponseResult;
import com.rty.springboot.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController extends AbstractContorller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/say",method = RequestMethod.GET)
    public ResultInfo<?> helloWorld() {
        LOGGER.info("say hello");
        try {
            ResultInfo resultInfo=createSuccessResult();
            resultInfo.setMessage("Hello World");
            return resultInfo;
        }catch (Exception e){
           LOGGER.info("say fail",e);
           return createFailResult("say fail");
        }
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
