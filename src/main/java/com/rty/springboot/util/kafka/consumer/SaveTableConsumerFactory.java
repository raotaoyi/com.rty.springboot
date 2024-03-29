package com.rty.springboot.util.kafka.consumer;

import com.rty.springboot.bean.KafkaSaveBean;
import com.rty.springboot.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaveTableConsumerFactory {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        KafkaSaveBean kafkaSaveBean = FileUtil.loadYamlConfig("/saveDb.yaml");
        if (kafkaSaveBean == null) {
            throw new RuntimeException("kafka save yaml fail");
        }
        kafkaSaveBean.getKafkaSaveDbs()
                .stream()
                .filter(kafkaSaveDb -> kafkaSaveDb.isEnable())
                .map(kafkaSaveDb -> new KafkaSaveTableConsumer(kafkaSaveDb.getGroupId(), createPreProcess(), createDataSaveStrategy(kafkaSaveDb), kafkaSaveDb.getTopic()))
                .forEach(kafkaSaveTableConsumer -> kafkaSaveTableConsumer.start());
    }

    public List<PreProcess> createPreProcess() {
        return null;

    }

    public List<DataSaveStrategy> createDataSaveStrategy(KafkaSaveBean.KafkaSaveDb saveDb) {
        List<DataSaveStrategy> dataSaveStrategies = new ArrayList<>();
        saveDb.getSaves().stream().forEach(save -> {
            if ("mysql".equalsIgnoreCase(save.getType())) {
                dataSaveStrategies.add(new MysqlSaveStrategyImpl(save.getParams(), jdbcTemplate));
            }
        });
        return dataSaveStrategies;
    }
}
