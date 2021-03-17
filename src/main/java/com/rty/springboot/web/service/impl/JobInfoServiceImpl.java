package com.rty.springboot.web.service.impl;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.JobTaskInfo;
import com.rty.springboot.web.mapper.JobInfoMapper;
import com.rty.springboot.web.service.IJobInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobInfoServiceImpl implements IJobInfoService {
    private final static Logger LOGGER = LoggerFactory.getLogger(JobInfoServiceImpl.class);

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Override
    public void addJobInfo(List<JobInfoBean> jobInfoBeans) {
        try {
            LOGGER.info("start add job info");
            Thread.sleep(3000);
            LOGGER.info("end add job info");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*        jobInfoMapper.insertJobInfo(jobInfoBeans);*/

    }

    @Override
    public void addJobTimeDetail(List<JobInfoBean> jobInfoBeans) {
        jobInfoMapper.insertJobTimeDetail(jobInfoBeans);

    }

    @Override
    public void addJobTask(List<JobTaskInfo> jobTaskInfos) {
        jobInfoMapper.insertJobTask(jobTaskInfos);
    }

    @Override
    @Cacheable(value = "rty_cache", key = "#root.methodName+'_'+#root.target.getArea(#param)")
    //@Cacheable(value = "rty_cache", key="#root.methodName+'_'+#param.get('area')")
    //@Cacheable(value = "rty_cache", key="#root.methodName+'_'+#param.get('area')",
    //        condition = "#param.get('area')=='all'")
    public int getJobCount(Map<String, String> param) {
        return jobInfoMapper.queryJobCount(param);
    }

    @Override
    public List<JobInfoBean> getJobInfos(Map<String, String> param) {
        return jobInfoMapper.queryJobInfos(param);
    }

    private String getArea(Map<String, String> param) {
        return param.get("area");

    }
}
