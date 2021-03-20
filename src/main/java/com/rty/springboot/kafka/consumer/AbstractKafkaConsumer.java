package com.rty.springboot.kafka.consumer;


import java.util.Properties;

public abstract class AbstractKafkaConsumer {
    private boolean isStop = true;
    private String groupId;
    private String[] topics;

    public AbstractKafkaConsumer(String groupId, String... topics) {
        this.groupId = groupId;
        this.topics = topics;

    }

    private Properties getProperties() {
        Properties properties = new Properties();
        return properties;
    }

    private String getBootStrapServers() {
        return null;
    }

    private void stop() {

    }

    private void start() {
        if (!isStop) {
            throw new RuntimeException("consumer is running");
        }


    }

    private void consumer() {

    }

    private void submitOffset() {

    }

    protected abstract void process();

    private void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
