package com.rty.springboot.common.exception;

import com.huawei.shiro.jwt.domain.result.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobeHander {

    @ExceptionHandler
    public ResponseResult handerException(HttpServletRequest request, Exception e) {
        return null;
    }
}
