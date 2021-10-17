package com.rty.springboot.util.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "sb.fanout.A")
public class FanoutReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("FanoutReceiver:" + hello);
    }
}
