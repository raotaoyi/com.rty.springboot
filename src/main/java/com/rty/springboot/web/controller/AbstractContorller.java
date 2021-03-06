package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ResultInfo;


public class AbstractContorller {

    public ResultInfo<?> createSuccessResult() {
        return new ResultInfo<>();
    }

    public ResultInfo<?> createSuccessResult(String message) {
        return new ResultInfo<>(message);
    }

    public ResultInfo<?> createFailResult(String message) {
        return new ResultInfo<>(message);
    }

}
