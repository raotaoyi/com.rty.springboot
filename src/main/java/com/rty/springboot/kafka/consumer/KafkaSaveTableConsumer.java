package com.rty.springboot.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;

public class KafkaSaveTableConsumer extends AbstractKafkaConsumer {

    private List<Preprocess> preprocesses;

    private List<DataSaveStrategy> dataSaveStrategies;

    public KafkaSaveTableConsumer(String groupId,
                                  List<Preprocess> preprocesses,
                                  List<DataSaveStrategy> dataSaveStrategies,
                                  String... topics) {
        super(groupId, topics);
        this.preprocesses = preprocesses;
        this.dataSaveStrategies = dataSaveStrategies;
    }

    @Override
    protected void process(ConsumerRecords<String, String> records) {

    }

}
