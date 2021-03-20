package com.rty.springboot.web.controller;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.util.SyncTaskRunner;
import com.rty.springboot.web.service.IJobInfoService;
import com.rty.springboot.web.service.IRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/test")
public class TestController extends AbstractContorller {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IJobInfoService jobInfoService;

    @Autowired
    private IRuleService ruleService;

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public ResultInfo<?> testSync() {
        try {
            LOGGER.info("start sync add job");
            ResultInfo resultInfo = createSuccessResult("test sync successful");
            int groupId = SyncTaskRunner.createGroup();
            SyncTaskRunner.add("addJobInfo", () -> jobInfoService.addJobInfo(new ArrayList<>()), groupId);
            SyncTaskRunner.add("addJobInfo2", () -> {
                throw new RuntimeException("add job fail");
            }, groupId);
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

    @RequestMapping(value = "/sync2", method = RequestMethod.GET)
    public ResultInfo<?> testSync2() {
        try {
            LOGGER.info("start sync add job");
            ResultInfo resultInfo = createSuccessResult("test sync successful");
            Map<String, String> param = new HashMap<>();
            Future<List<JobInfoBean>> future = SyncTaskRunner.future(() -> {
                return jobInfoService.getJobInfos(param);
            });
            Future<List<JobInfoBean>> future2 = SyncTaskRunner.future(() -> {
                return jobInfoService.getJobInfos(param);
            });

            List<JobInfoBean> result1 = future.get();
            System.out.println("task1 is finish " + result1.size());
            List<JobInfoBean> result2 = future2.get();
            System.out.println("task2 is finish " + result2.size());

            LOGGER.info("end sync add job");
            return resultInfo;
        } catch (Exception e) {
            LOGGER.info("test sync fail", e);
            return createFailResult("test sync fail");
        }
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public ResultInfo<?> testDb() {
        try {
            LOGGER.info("start sync add job");
            ResultInfo resultInfo = createSuccessResult("test sync successful");
            resultInfo.setData(ruleService.getRuleInfos(new HashMap<>()));
            LOGGER.info("end sync add job");
            return resultInfo;
        } catch (Exception e) {
            LOGGER.info("test sync fail", e);
            return createFailResult("test sync fail");
        }
    }

}