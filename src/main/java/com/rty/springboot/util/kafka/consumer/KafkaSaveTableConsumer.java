package com.rty.springboot.util.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;

public class KafkaSaveTableConsumer extends AbstractKafkaConsumer {

    public KafkaSaveTableConsumer(String groupId, String topic,
                                  List<PreProcess> preProcesses, List<DataSaveStrategy> dataSaveStrategies) {
        super(groupId, topic);
    }

    @Override
    public void process(ConsumerRecords<String, String> consumerRecords) {
        if (consumerRecords == null) {
            return;
        }
        consumerRecords.forEach(record -> {
            System.out.println(String.format("topic:%s,分区,%d,偏移量,%d" + "key:%s,value:%s", record.topic(), record.partition(),
                    record.offset(), record.key(), record.value()));

        });

    }
}
