package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.util.SyncTaskRunner;
import com.rty.springboot.web.service.IJobInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/test")
public class TestController extends AbstractContorller {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IJobInfoService jobInfoService;

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public ResultInfo<?> testSync() {
        try {
            LOGGER.info("start sync add job");
            ResultInfo resultInfo = createSuccessResult("test sync successful");
            int groupId = SyncTaskRunner.createGroup();
            SyncTaskRunner.add("addJobInfo", () -> jobInfoService.addJobInfo(new ArrayList<>()), groupId);
            SyncTaskRunner.add("addJobInfo2", () -> {throw new RuntimeException("add job fail");}, groupId);
            SyncTaskRunner.add("addJobInfo3", () -> jobInfoService.addJobInfo(new ArrayList<>()), groupId);
            SyncTaskRunner.add("addJobInfo4", () -> jobInfoService.addJobInfo(new ArrayList<>()), groupId);
            SyncTaskRunner.start(groupId);
            LOGGER.info("end sync add job");
            return resultInfo;
        } catch (Exception e) {
            LOGGER.info("test sync fail", e);
            return createFailResult("test sync fail");
        }
    }

}