package com.rty.springboot.web.service.impl;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.JobTaskInfo;
import com.rty.springboot.web.mapper.mc.JobInfoMapper;
import com.rty.springboot.web.service.IJobInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * todo 在不同状态下的本地缓存的创建，以及创建key值
     */
    @Override
    @Cacheable(value = "rty_cache", key = "#root.methodName+'_'+#root.target.getArea(#param)")
    //@Cacheable(value = "rty_cache", key="#root.methodName+'_'+#param.get('area')")
    //@Cacheable(value = "rty_cache", key="#root.methodName+'_'+#param.get('area')",
    //        condition = "#param.get('area')=='all'")
    public int getJobCount(Map<String, String> param) {
        return jobInfoMapper.queryJobCount(param);
    }

    /**
     * 在多数据源的情况下要指定具体的数据源的事物管理器，不然会出现错误
     */
    @Override
    @Transactional(transactionManager = "dataSourceTransactionManager")
    public List<JobInfoBean> getJobInfos(Map<String, String> param) {
        return jobInfoMapper.queryJobInfos(param);
    }

    private String getArea(Map<String, String> param) {
        return param.get("area");

    }
}
