package com.rty.springboot.common.config.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaProducerConfig {
    @Autowired
    private Environment env;

    @Bean
    public KafkaProducer<String, String> kafkaProducer() {
        Properties properties = getProperties();
        return new KafkaProducer<String, String>(properties);
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", env.getProperty("bootstrap.servers"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }


}
