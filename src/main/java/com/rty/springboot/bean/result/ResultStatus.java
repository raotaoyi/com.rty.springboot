package com.rty.springboot.bean.result;

public enum  ResultStatus {
    SUCCESS("ok",1),
    PASSWORDERROR("password is error",1),
    USERNAMEERROR("username is error",1);
    private String message;
    private Integer status;


    ResultStatus(String message, int status) {
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
