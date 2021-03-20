package com.rty.springboot.common.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.skb")
@Setter
@Getter
public class SkbDataSourceConfig {
    private String url;
    private String username;
    private String password;

    @Autowired
    private Environment env;

    //配置主数据库
    @Bean("skbDataSource")
    @Primary
    public DataSource skbDataSource() {
        HikariDataSource skbDB = new HikariDataSource();
        skbDB.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        skbDB.setJdbcUrl(url);
        skbDB.setPassword(password);
        skbDB.setUsername(username);
        return skbDB;
    }
}
