package com.rty.springboot.util.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "sb.hello")
public class HelloReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("helloReceiver:" + hello);
    }
}
