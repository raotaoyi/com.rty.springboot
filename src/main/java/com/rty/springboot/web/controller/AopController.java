package com.rty.springboot.web.controller;

import com.rty.springboot.common.config.datasource.DBContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AopController extends AbstractContorller {

    @Pointcut("(execution(* com.rty.springboot.web.service..*.select*(..)) " +
            "|| execution(* com.rty.springboot.web.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("execution(* com.rty.springboot.web.service..*.insert*(..)) " +
            "|| execution(* com.rty.springboot.web.service..*.add*(..)) " +
            "|| execution(* com.rty.springboot.web.service..*.update*(..)) " +
            "|| execution(* com.rty.springboot.web.service..*.edit*(..)) " +
            "|| execution(* com.rty.springboot.web.service..*.delete*(..)) " +
            "|| execution(* com.rty.springboot.web.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.read();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.write();
    }

    @Pointcut("(execution(* com.rty.springboot.web.controller..*.*(..)))")
    public void pointCut() {

    }

    /**
     * 如果没有shiro认证框架，并且有相应的返回信息，并且统一的controller权限验证
     */
    @Around("pointCut()")
    public Object run(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isPermission()) {
            return joinPoint.proceed();
        } else {
            return createFailResult("permission is not allow");
        }
    }

    @Before("pointCut()")
    public void runBefore() throws Throwable {

    }

    /**
     * 获取request中相应的验证信息，进行验证接口的token验证等
     */
    private boolean isPermission() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return true;
    }
}
