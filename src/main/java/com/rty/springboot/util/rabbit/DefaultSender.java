package com.rty.springboot.util.rabbit;

import com.rty.springboot.util.RmConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.Action;

@Component
public class DefaultSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * TODO普通消息处理
     */
    public void sendDefault(String msg) {
        String sendMsg = msg + "-----" + System.currentTimeMillis();
        System.out.println("Sender:" + sendMsg);
        //发送消息至缺省交换器（默认）
        this.rabbitTemplate.convertAndSend(RmConst.QUEUE_HELLO, sendMsg);
    }

    /**
     * TODO消息确认--加入消息确认
     */
    public void sendConfirm(String msg) {
        String sendMsg = msg + "-----" + System.currentTimeMillis();
        System.out.println("Sender:" + sendMsg);
        this.rabbitTemplate.convertAndSend(RmConst.QUEUE_USER, sendMsg);
    }
}
