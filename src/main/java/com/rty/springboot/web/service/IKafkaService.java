package com.rty.springboot.web.service;

public interface IKafkaService {

    void sent(String topic, Object object);
}
