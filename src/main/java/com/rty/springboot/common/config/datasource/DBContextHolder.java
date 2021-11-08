package com.rty.springboot.common.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class DBContextHolder {

    public final static Logger logger = LoggerFactory.getLogger(DBContextHolder.class);

    /*线程安全的工具类，线程隔离*/
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

    public static void skb() {
        set(DBTypeEnum.SKB);
        logger.info("its aready master db");

    }

    /**
     * 清理
     */
    public static void clear() {
        contextHolder.remove();
    }
}
