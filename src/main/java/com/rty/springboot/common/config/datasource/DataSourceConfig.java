package com.rty.springboot.common.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    //配置主数据库
    @Bean("readDB")
    public DataSource getReadDataSource() {
        HikariDataSource readDB = new HikariDataSource();
        readDB.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        readDB.setJdbcUrl(env.getProperty("spring.datasource.read.url"));
        readDB.setPassword(env.getProperty("spring.datasource.read.password"));
        readDB.setUsername(env.getProperty("spring.datasource.read.username"));
        return readDB;
    }

    //配置从数据库
    @Bean("writeDB")
    public DataSource getWriteDataSource() {
        HikariDataSource writeDB = new HikariDataSource();
        writeDB.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        writeDB.setJdbcUrl(env.getProperty("spring.datasource.write.url"));
        writeDB.setUsername(env.getProperty("spring.datasource.write.username"));
        writeDB.setPassword(env.getProperty("spring.datasource.write.password"));
        return writeDB;
    }

    @Bean("routingDataSource")
    public DataSource myRoutingDataSource(@Qualifier("readDB") DataSource readDataSource,
                                          @Qualifier("writeDB") DataSource writeDataSource
    ) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.READ, readDataSource);
        targetDataSources.put(DBTypeEnum.WRITE, writeDataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(readDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

    /**
     * 不同的事物数据源绑定不同的事物管理器（错误使用没有效果），
     * 多数据源的事务时，要指定事务管理器，不然程序不知道使用哪一个
     */
    @Bean("dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }

}
