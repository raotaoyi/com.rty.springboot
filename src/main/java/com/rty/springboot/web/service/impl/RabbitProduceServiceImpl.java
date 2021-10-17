package com.rty.springboot.web.service.impl;

import com.rty.springboot.web.service.IRabbitProduceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RabbitProduceServiceImpl implements IRabbitProduceService {

    @Autowired
    private RabbitTemplate template;

    @Override
    public void direSend(String msg) {
        template.convertAndSend("", "", "");
    }
}
