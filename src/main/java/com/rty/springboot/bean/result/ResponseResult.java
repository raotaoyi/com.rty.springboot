package com.rty.springboot.bean.result;

public class ResponseResult {
    private String message;
    private Integer status;

    public ResponseResult(String message, int status) {
        this.message=message;
        this.status=status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
