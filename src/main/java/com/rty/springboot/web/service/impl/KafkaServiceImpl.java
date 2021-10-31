package com.rty.springboot.web.service.impl;

import com.rty.springboot.bean.User;
import com.rty.springboot.web.service.IKafkaService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KafkaServiceImpl implements IKafkaService {

//    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public void sent(String topic, Object object) {
        User demoUser = (User) object;
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null,
                System.currentTimeMillis(), demoUser.getId() + "_" + System.currentTimeMillis()
                , demoUser.toString());
        kafkaProducer.send(record, (recordMetadata, e) -> {
            if (null != e) {
                e.printStackTrace();
            }
            if (null != recordMetadata) {
                System.out.println("offet:" + recordMetadata.offset() + "-" + "partition:" +
                        recordMetadata.partition() + "-" + "topic:" + recordMetadata.topic());
            }
            System.out.println(demoUser.getId() + "数据[" + record + "]已发送");
        });

    }
}
