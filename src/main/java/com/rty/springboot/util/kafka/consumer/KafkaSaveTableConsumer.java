package com.rty.springboot.util.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KafkaSaveTableConsumer extends AbstractKafkaConsumer {

    private List<DataSaveStrategy> dataSaveStrategies;
    private List<PreProcess> preProcesses;

    public KafkaSaveTableConsumer(String groupId, List<PreProcess> preProcesses,
                                  List<DataSaveStrategy> dataSaveStrategies, String... topic) {
        super(groupId, topic);
        this.dataSaveStrategies = dataSaveStrategies;
        this.preProcesses = preProcesses;
    }

    @Override
    public void process(ConsumerRecords<String, String> consumerRecords) {
        List<JSONObject> beans = new ArrayList<>();
        if (consumerRecords == null) {
            return;
        }
        for (ConsumerRecord record : consumerRecords) {
            try {
                JSONObject bean = JSONObject.parseObject(record.value().toString());
                beans.add(bean);
            } catch (Exception e) {
                continue;
            }
        }
        this.dataSaveStrategies.forEach(dataSaveStrategy -> dataSaveStrategy.save(beans));
    }
}
