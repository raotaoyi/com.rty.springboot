package com.rty.springboot.kafka.consumer;

public class KafkaSaveTableConsumer extends AbstractKafkaConsumer {

    public KafkaSaveTableConsumer(String groupId, String... topics) {
        super(groupId, topics);
    }

    @Override
    protected void process() {

    }
}
