package com.rty.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rty.springboot.web.mapper")
public class RTYSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RTYSpringBootApplication.class, args);

    }
}
