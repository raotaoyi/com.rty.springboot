package com.rty.springboot.util.rabbit;

import com.rty.springboot.util.RmConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String msg = "i am email message msg======";
        System.out.println("TopicSender send the lst:" + msg);
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC, RmConst.RK_EMAIL, msg);

        String msg2 = "i am user message msg======";
        System.out.println("TopicSender send the 2st:" + msg2);
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC, RmConst.RK_USER, msg2);

        //失败者通知
        String msg3 = "i am error message msg======";
        System.out.println("TopicSender send the 3st:" + msg3);
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC, "errorkey", msg3);

    }

}
