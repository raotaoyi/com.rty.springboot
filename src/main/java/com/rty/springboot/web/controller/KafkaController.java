package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.bean.User;
import com.rty.springboot.web.service.IKafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/kafka")
public class KafkaController extends AbstractContorller {
    private static Logger logger = LoggerFactory.getLogger(RabbitMqController.class);

    @Autowired
    private IKafkaService kafkaService;

    /**
     * 普通类型测试
     */
    @GetMapping("/send")
    public ResultInfo<?> kafkaSend(HttpServletRequest request) {
        ResultInfo result = createSuccessResult("dire send");
        try {
            User user = new User();
            user.setId("1");
            user.setUsername("jack");
            user.setPassword("777777");
            kafkaService.sent("test", user);
        } catch (Exception e) {
            logger.info("dire send data fail", e);
        }
        return result;
    }
}
