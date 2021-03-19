package com.rty.springboot.kafka.consumer;


import java.util.Properties;

public abstract class AbstractKafkaConsumer {

    public AbstractKafkaConsumer(String groupId, String... topics) {

    }

    private Properties getProperties() {
        return null;
    }

    private String getBootStrapServers() {
        return null;
    }

    private void stop() {

    }

    private void start() {

    }

    private void consumer() {

    }

    private void submitOffset() {

    }

    protected abstract void process();

    private void sleep() {

    }
}
