package com.rty.springboot.util.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class AbstractKafkaConsumer {
    private static Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumer.class);
    private KafkaConsumer<String, String> kafkaConsumer;

    private Executor executor = Executors.newSingleThreadExecutor();

    private volatile boolean isStop = true;

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put("enable.auto.commit", false);//表明消费者是否是自动提交偏移量，默认为true
        properties.put("max.poll.records", 500);//控制每次pull方法返回的记录数量，默认为500
        return properties;
    }

    public AbstractKafkaConsumer(String groupId, String... topics) {
        Properties properties = getProperties();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaConsumer.subscribe(Arrays.asList(topics));
    }

    public synchronized void stop() {
        if (isStop) {
            throw new RuntimeException("the consumer is not running");
        }

    }

    public synchronized void start() {
        if (!isStop) {
            throw new RuntimeException("the consumer is already running");
        }
        isStop = false;
        executor.execute(this::consumer);
    }

    public void consumer() {
        final String thread = Thread.currentThread().getId() + "_" + System.currentTimeMillis();
        int failTimes = 0;
        while (!isStop) {
            ConsumerRecords<String, String> consumerRecords = this.kafkaConsumer.poll(500);
            try {
                if (consumerRecords.isEmpty()) {
                    sleep(2000);
                    continue;
                }
                process(consumerRecords);
                submitOffsets(consumerRecords);
                failTimes = 0;
            } catch (Exception e) {
                failTimes++;
                if (failTimes > 6) {
                    submitOffsets(consumerRecords);
                    failTimes = 0;
                }
                logger.error(thread + " is error,the cause reason is " + e.getMessage());
                sleep(2000);
            }
        }
    }

    public void submitOffsets(ConsumerRecords<String, String> consumerRecords) {
        if (consumerRecords == null) {
            return;
        }
        Map<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>();
        consumerRecords.forEach(consumerRecord -> {
            offsetMap.put(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()), new OffsetAndMetadata(consumerRecord.offset() + 1));
        });
        kafkaConsumer.commitSync(offsetMap);
    }

    public abstract void process(ConsumerRecords<String, String> consumerRecords);

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
