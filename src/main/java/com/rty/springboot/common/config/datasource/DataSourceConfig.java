package com.rty.springboot.common.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    //配置主数据库
    @Bean("readDB")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource getMasterDataSource() {
        return DataSourceBuilder.create().build();

    }

    //配置从数据库
    @Bean("writeDB")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource getSlaveDataSource() {
        return DataSourceBuilder.create().build();
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

    @Bean("dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }

}
