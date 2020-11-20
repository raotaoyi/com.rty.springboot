package com.rty.springboot.common.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class DBContextHolder {

    public final static Logger logger = LoggerFactory.getLogger(DBContextHolder.class);

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void read() {
        set(DBTypeEnum.READ);
        logger.info("its aready slave db");

    }

    public static void write() {
        set(DBTypeEnum.WRITE);
        logger.info("its aready master db");

    }
}
