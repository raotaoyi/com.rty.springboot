package com.rty.springboot.web.controller;


import com.huawei.shiro.jwt.web.service.UserService;
import com.rty.springboot.common.jwt.JwtHelper;
import com.rty.springboot.bean.UserInfo;
import com.rty.springboot.bean.audience.Audience;
import com.rty.springboot.bean.result.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class AccessTokenController {

    private static final com.rty.springboot.bean.result.ResultStatus ResultStatus = ;
    @Autowired
    private UserService userService;

    @Autowired
    private Audience audience;

    /*
     *  Server端保存就是有状态的，浏览器端保存就是无状态的。
     *  如果为get请求时，后台接收参数的注解应该为RequestParam，
     *  如果为post请求时，Content-Type:application/json则后台接收参数的注解就是为RequestBody（可以映射为实体类，也可以为map类型）
     *  如果为post请求时，Content-Type:application/x-www-form-urlencoded则后台接收参数的注解就是为RequestParam,使用较少
     * */
    @RequestMapping(value = "/{auth}/token", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResponseResult AuthAccessToken(@PathVariable("auth") String auth, @RequestBody Map<String, String> data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("auth:" + auth);
        UserInfo userInfo = userService.getUser(data.get("username"));
        if (userInfo == null) {
            return new ResponseResult(ResultStatus.USERNAMEERROR.getMessage(), ResultStatus.USERNAMEERROR.getStatus());
        } else {
            String pass = userInfo.getPass();
            if (!(pass.equals(data.get("password")))) {
                return new ResponseResult(ResultStatus.PASSWORDERROR.getMessage(), ResultStatus.PASSWORDERROR.getStatus());
            }

            // 拼装accessToken
            String accessToken = JwtHelper.createJWT(userInfo.getName(), userInfo.getId(),
                    "", audience.getName(), "", audience.getExpiresSecond(), audience.getBase64Secret());
            System.out.println("accessToken:" + accessToken);

            Cookie cookie = new Cookie("username", userInfo.getName());
            cookie.setMaxAge(-1); //设置cookie的过期时间
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("id", userInfo.getId().toString());
            cookie.setMaxAge(-1); //设置cookie的过期时间
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("token", accessToken);
            cookie.setMaxAge(-1); //设置cookie的过期时间
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ResponseResult(ResultStatus.SUCCESS.getMessage(), ResultStatus.SUCCESS.getStatus());
        }

    }
}
