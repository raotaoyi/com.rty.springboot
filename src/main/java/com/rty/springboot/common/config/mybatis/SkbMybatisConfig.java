package com.rty.springboot.common.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 根据相应的Mapper层进行数据库的切换
 * <p>
 * 场景:项目中既有mc的数据库，又有skb数据库
 */
@Configuration
@MapperScan(basePackages = "com.rty.springboot.web.mapper.skb", sqlSessionFactoryRef = "skbSqlSessionFactory")
public class SkbMybatisConfig {

    @Resource(name = "skbDataSource")
    private DataSource dataSource;

    @Bean("skbSqlSessionFactory")
    @Primary
    public SqlSessionFactory skbSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/skb/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("skbSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate skbSqlSessionTemplate(@Qualifier("skbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
