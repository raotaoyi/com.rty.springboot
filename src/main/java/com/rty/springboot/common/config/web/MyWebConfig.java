package com.rty.springboot.common.config.web;

import com.rty.springboot.common.Interceptor.McInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 和WebMvcConfigurerSupport一起使用的话，会失效
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private McInterceptor mcInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mcInterceptor);
    }
}
