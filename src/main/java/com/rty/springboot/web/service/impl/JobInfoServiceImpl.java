package com.rty.springboot.web.service.impl;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.JobTaskInfo;
import com.rty.springboot.web.mapper.JobInfoMapper;
import com.rty.springboot.web.service.IJobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobInfoServiceImpl implements IJobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Override
    public void addJobInfo(List<JobInfoBean> jobInfoBeans) {
        jobInfoMapper.insertJobInfo(jobInfoBeans);

    }

    @Override
    public void addJobTimeDetail(List<JobInfoBean> jobInfoBeans) {
        jobInfoMapper.insertJobTimeDetail(jobInfoBeans);

    }

    @Override
    public void addJobTask(List<JobTaskInfo> jobTaskInfos) {
        jobInfoMapper.insertJobTask(jobTaskInfos);
    }
}
