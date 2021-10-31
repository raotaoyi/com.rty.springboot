package com.rty.springboot.util.kafka.consumer;

import java.util.List;
import java.util.Map;

public interface DataSaveStrategy {
    void save(List<? extends Map<String, ?>> beans);
}
