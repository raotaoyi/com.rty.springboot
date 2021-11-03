package com.rty.springboot.util.kafka.consumer;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MysqlSaveStrategyImpl implements DataSaveStrategy {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    public MysqlSaveStrategyImpl(Map<String, String> params, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = params.get("table");
    }

    @Override
    public void save(List<? extends Map<String, ?>> beans) {

        executeSqlWithRetry();
    }

    /**
     * 执行20次，如果成功了直接返回，失败了重试20次，每次间隔30秒，
     */
    private void executeSqlWithRetry() {

        for (int i = 0; i < 20; i++) {

        }


    }
}
