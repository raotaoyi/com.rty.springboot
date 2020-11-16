package com.rty.springboot.common.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
