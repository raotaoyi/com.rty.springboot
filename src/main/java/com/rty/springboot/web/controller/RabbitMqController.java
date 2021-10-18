package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.util.rabbit.DefaultSender;
import com.rty.springboot.util.rabbit.TopicSender;
import com.rty.springboot.web.service.IRabbitProduceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController extends AbstractContorller {

    private static Logger logger = LoggerFactory.getLogger(RabbitMqController.class);

    @Autowired
    private DefaultSender defaultSender;
    @Autowired
    private TopicSender topicSender;

    /**
     * 普通类型测试
     */
    @GetMapping("hello")
    public ResultInfo<?> direSendHello(HttpServletRequest request) {
        ResultInfo result = createSuccessResult("dire send");
        try {
            defaultSender.sendDefault("hello world dire");
        } catch (Exception e) {
            logger.info("dire send data fail", e);
        }
        return result;
    }

    @GetMapping("user")
    public ResultInfo<?> direSendUser(HttpServletRequest request) {
        ResultInfo result = createSuccessResult("dire send");
        try {
            defaultSender.sendConfirm("user world dire");
        } catch (Exception e) {
            logger.info("dire send data fail", e);
        }
        return result;
    }

    @GetMapping("topic")
    public ResultInfo<?> topicSend(HttpServletRequest request) {
        ResultInfo result = createSuccessResult("topic send");
        try {
            topicSender.send();
        } catch (Exception e) {
            logger.info("dire send data fail", e);
        }
        return result;
    }
}

