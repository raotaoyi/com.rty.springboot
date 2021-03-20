package com.rty.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.rty.springboot.web.mapper")
@EnableScheduling
@EnableAsync
@EnableCaching
public class RTYSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RTYSpringBootApplication.class, args);

    }
}
