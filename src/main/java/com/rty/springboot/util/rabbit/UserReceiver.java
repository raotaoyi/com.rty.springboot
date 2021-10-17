package com.rty.springboot.util.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;


@Component("rabbitUserReceiver")
public class UserReceiver implements ChannelAwareMessageListener {
/*    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver2:" + hello);
    }*/

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());
            System.out.println("UserReceiver>>>>>>>>接受到消息" + msg);
            try {
                //消息的确认,确认之后，内存和磁盘都会被消费，清空
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("UserReceiver>>>>>>消息已消费");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                //消息以拒绝
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                System.out.println("UserReceiver>>>>>>拒绝消息，要求Mq重新派发");
                throw e;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
