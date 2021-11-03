package com.rty.springboot.util.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KafkaSaveTableConsumer extends AbstractKafkaConsumer {

    private List<DataSaveStrategy> dataSaveStrategies;

    public KafkaSaveTableConsumer(String groupId, String topic,
                                  List<PreProcess> preProcesses, List<DataSaveStrategy> dataSaveStrategies) {
        super(groupId, topic);
        this.dataSaveStrategies = dataSaveStrategies;
    }

    @Override
    public void process(ConsumerRecords<String, String> consumerRecords) {
        List<? extends Map<String, ?>> beans = new ArrayList<>();
        if (consumerRecords == null) {
            return;
        }
        consumerRecords.forEach(record -> {
            System.out.println(String.format("topic:%s,分区,%d,偏移量,%d" + "key:%s,value:%s", record.topic(), record.partition(),
                    record.offset(), record.key(), record.value()));
        });
        this.dataSaveStrategies.forEach(dataSaveStrategy -> dataSaveStrategy.save(beans));

    }
}
