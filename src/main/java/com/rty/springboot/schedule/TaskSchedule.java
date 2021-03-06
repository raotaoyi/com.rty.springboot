package com.rty.springboot.schedule;

import com.alibaba.fastjson.JSONObject;
import com.rty.springboot.util.HttpUtil;
import com.rty.springboot.util.SyncTaskRunner;
import com.rty.springboot.web.service.ICacheService;
import com.rty.springboot.web.service.IJobInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Async 类说明:定时器如果不使用异步，如果有进程进行阻塞，那么后面的定时到时间就是排队，导致，
 * 一些任务不再规定的时间进行处理导致数据的滞后性
 */
@Component
public class TaskSchedule {
    private static final Log LOGGER = LogFactory.getLog(TaskSchedule.class);

    private ICacheService cacheService;
    @Autowired
    private IJobInfoService jobInfoService;

    @Scheduled(cron = "* 32 23 ? * *")
    @Async
    public void insertJobInfo() {
        LOGGER.info("start sync job");
    }

    @Scheduled(cron = "10 45 22 ? * *")
    @Async
    public void insertJobTimeDetailInfo() {
        LOGGER.info("start sync job time datail");
    }

    @Scheduled(cron = "0/30 * * ? * *")
    @Async
    public void insertProjectInfo() {
        LOGGER.info("start project info detail");
        String url = "";
        JSONObject object = JSONObject.parseObject("");
        /*        HttpUtil.post(url, object, "UTF-8");*/
    }

    /**
     * 凌晨30清除缓存
     */
    @Scheduled(cron = "* 30 0 ? * *")
    @Async
    public void clearCache() {
        cacheService.clearCache();
    }
}
