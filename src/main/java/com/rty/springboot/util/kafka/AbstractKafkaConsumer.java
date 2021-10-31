package com.rty.springboot.util.kafka;

import com.rty.springboot.web.controller.RabbitMqController;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class AbstractKafkaConsumer {
    private static Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumer.class);
    private KafkaConsumer<String, String> kafkaConsumer;

    private Executor executor = Executors.newSingleThreadExecutor();

    private volatile boolean isStop = true;

    private Properties getProperties() {
        Properties properties = new Properties();
        return properties;
    }

    public AbstractKafkaConsumer(String groupId, String... topics) {
        Properties properties = getProperties();
        properties.setProperty("", groupId);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        this.kafkaConsumer = kafkaConsumer;
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
        int failTimes = 0;
        while (!isStop) {
            ConsumerRecords<String, String> consumerRecords = this.kafkaConsumer.poll(200);
            try {
                if (consumerRecords.isEmpty()) {
                    continue;
                }
                process(consumerRecords);
                submitOffsets(consumerRecords);
            } catch (Exception e) {
                failTimes++;
                if (failTimes > 6) {
                    submitOffsets(consumerRecords);
                    failTimes = 0;
                }
                logger.error(e.getMessage());
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

    public abstract void process(ConsumerRecords consumerRecords);

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
