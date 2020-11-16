package com.rty.springboot.web.controller;

import com.rty.springboot.bean.result.ResultStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("verified/index/index")
    public ResultStatus index() {
        return null;
    }
}
