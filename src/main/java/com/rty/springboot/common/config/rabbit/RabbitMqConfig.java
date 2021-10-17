package com.rty.springboot.common.config.rabbit;

import com.rty.springboot.util.RmConst;
import com.rty.springboot.util.rabbit.UserReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 1,使用顺序
 */
@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.publisher-confirm}")
    private boolean publisherconfirm;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualhost;

    @Autowired
    private UserReceiver rabbitUserReceiver;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host + ":" + port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        //消息发送确认，则这里必须设置为true
        connectionFactory.setPublisherConfirms(publisherconfirm);
        return connectionFactory;

    }

    //rabbitAdmin类封装了对RabbitMq的管理操作
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    //使用template
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //失败通知
        template.setMandatory(true);
        //发送方确认
        template.setConfirmCallback(confirmCallback());
        //失败回调
        template.setReturnCallback(returnCallback());
        return template;
    }

    /*使用了RabbitMq系统的缺省的交换器(direct交换器：完全匹配)*/
    //申明队列
    @Bean
    public Queue helloQueue() {
        return new Queue(RmConst.QUEUE_HELLO);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(RmConst.QUEUE_USER);
    }

    /*以下验证 topic Exchange*/
    @Bean
    public Queue queueEmailMessage() {
        return new Queue(RmConst.QUEUE_TOPIC_EMAIL);
    }

    @Bean
    public Queue queueUserMessage() {
        return new Queue(RmConst.QUEUE_TOPIC_USER);
    }

    //声明交换器（topic交换器）

    /**
     * #和，.将路由器分为几个标识符，*匹配1个，#匹配一个或者多个
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RmConst.EXCHANGE_TOPIC);
    }

    //绑定关系
    @Bean
    public Binding bindingEmailExchangeMessage() {
        return BindingBuilder.bind(queueEmailMessage()).to(exchange()).with("sb.*.email");
    }

    @Bean
    public Binding bindingUserExchangeMessage() {
        return BindingBuilder.bind(queueUserMessage()).to(exchange()).with("sb.*.user");
    }
    /*以上是验证topic exchange*/

    /*以下是验证fanout exchange*/
    //申明队列(fanout交换器)
    @Bean("aMessage")
    public Queue aMessage() {
        return new Queue("sb.fanout.A");
    }

    @Bean("aFanoutExchange")
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RmConst.EXCHANGE_FANOUT);
    }

    //绑定关系
    @Bean
    public Binding bindingExchangeA(@Qualifier("aMessage") Queue aMessage,
                                    @Qualifier("aFanoutExchange") FanoutExchange fanoutExchange) {

        return BindingBuilder.bind(aMessage).to(fanoutExchange);
    }
    /*以上是验证topic exchange*/

    /**
     * 消费者确认
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        //TODO 绑定了这个sb.user的队列
        container.setQueues(userQueue());
        //TODO 手动提交
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //TODO  发送方确认
        container.setMessageListener(rabbitUserReceiver);
        return container;

    }

    /*生产者发送确认*/
    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("发送者确认发送给mq成功");
                } else {
                    //处理失败的消息
                    System.out.println("发送者发送给给mq失败，考虑重发:" + cause);
                }

            }
        };
    }

    /*失败者通知*/
    @Bean
    public RabbitTemplate.ReturnCallback returnCallback() {
        return new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message,
                                        int replyCode,
                                        String replyText,
                                        String exchange,
                                        String routingKey) {
                System.out.println("无法路由的消息，需要考虑另外处理");
                System.out.println("returned replyText:" + replyText);
                System.out.println("returned exchange:" + exchange);
                System.out.println("returned routingKey:" + routingKey);
                String msgJson = new String(message.getBody());
                System.out.println("Returned Message:" + msgJson);
            }
        };
    }


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPublisherconfirm(boolean publisherconfirm) {
        this.publisherconfirm = publisherconfirm;
    }

    public void setVirtualhost(String virtualhost) {
        this.virtualhost = virtualhost;
    }
}
