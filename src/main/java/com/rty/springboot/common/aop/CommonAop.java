package com.rty.springboot.common.aop;

import com.rty.springboot.common.config.datasource.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonAop {

    @Pointcut("(execution(* com.huawei.shiro.jwt.web.service..*.select*(..)) " +
            "|| execution(* com.huawei.shiro.jwt.web.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("execution(* com.huawei.shiro.jwt.web.service..*.insert*(..)) " +
            "|| execution(* com.huawei.shiro.jwt.web.service..*.add*(..)) " +
            "|| execution(* com.huawei.shiro.jwt.web.service..*.update*(..)) " +
            "|| execution(* com.huawei.shiro.jwt.web.service..*.edit*(..)) " +
            "|| execution(* com.huawei.shiro.jwt.web.service..*.delete*(..)) " +
            "|| execution(* com.huawei.shiro.jwt.web.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
