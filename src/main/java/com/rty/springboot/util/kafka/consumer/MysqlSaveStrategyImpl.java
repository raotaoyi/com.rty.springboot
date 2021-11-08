package com.rty.springboot.util.kafka.consumer;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class MysqlSaveStrategyImpl implements DataSaveStrategy {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    public MysqlSaveStrategyImpl(Map<String, String> params, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = params.get("table");
    }

    @Override
    public void save(List<? extends Map<String, ?>> beans) {
        List<Object[]> values = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        Set<String> columnSets = new HashSet<>();
        beans.forEach(bean -> columnSets.addAll(bean.keySet()));
        columns.addAll(columnSets);
        beans.forEach(bean -> {
            Object[] object = new Object[columns.size()];
            int flag = 0;
            for (String key : columns) {
                object[flag] = bean.get(key);
                flag++;
            }
            values.add(object);
        });
        executeSqlWithRetry(columns, values);
    }

    /**
     * 执行20次，如果成功了直接返回，失败了重试20次，每次间隔30秒，
     */
    private void executeSqlWithRetry(List<String> columns, List<Object[]> values) {
        for (int i = 0; i < 20; i++) {
            try {
                //TODO 数据库操作
                String sql = "insert into";
                return;

            } catch (Exception e) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
