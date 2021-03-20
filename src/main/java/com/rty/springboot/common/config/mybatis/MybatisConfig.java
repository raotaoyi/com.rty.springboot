package com.rty.springboot.common.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan(basePackages = "com.rty.springboot.web.mapper.mc", sqlSessionFactoryRef = "mcSqlSessionFactory")
public class MybatisConfig {
    @Resource(name = "routingDataSource")
    private DataSource dataSource;

    @Bean("mcSqlSessionFactory")
    public SqlSessionFactory mcSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/mc/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    @Bean("mcSqlSessionTemplate")
    public SqlSessionTemplate mcSqlSessionTemplate(@Qualifier("mcSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
