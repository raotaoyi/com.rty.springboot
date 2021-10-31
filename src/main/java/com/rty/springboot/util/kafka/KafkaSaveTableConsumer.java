package com.rty.springboot.util.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;

public class KafkaSaveTableConsumer extends AbstractKafkaConsumer {

    public KafkaSaveTableConsumer(String groupId, String topic,
                                  List<PreProcess> preProcesses, List<DataSaveStrategy> dataSaveStrategies) {
        super(groupId, topic);
    }

    @Override
    public void process(ConsumerRecords consumerRecords) {

    }
}
