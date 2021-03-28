package com.rty.springboot.kafka.consumer;

import com.rty.springboot.bean.KafkaSaveBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * 使用java8的集合的聚合函数
 */
@Component
public class KafkaSaveTableConsumerFactory {

    @PostConstruct
    public void init() {
        KafkaSaveBean kafkaSaveBean = KafkaSaveBean.loadConfig("/saveDb.yaml");
        kafkaSaveBean.getKafkaSaveDbs().stream()
                .filter(KafkaSaveBean.KafkaSaveDb::isEnable)
                .map(kafkaSaveDb ->
                        new KafkaSaveTableConsumer(kafkaSaveDb.getGroupId(),
                                null,
                                null,
                                kafkaSaveDb.getTopic()))
                .forEach(KafkaSaveTableConsumer::start);
    }

}
