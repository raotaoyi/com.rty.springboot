package com.rty.springboot.web.service;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.JobTaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IJobInfoService {
    /**
     * 插入job信息
     */
    void addJobInfo(List<JobInfoBean> jobInfoBeans);

    /**
     * 插入job信息
     */
    void addJobTimeDetail(List<JobInfoBean> jobInfoBeans);

    /**
     * 插入task信息
     */
    void addJobTask(List<JobTaskInfo> jobTaskInfos);
}
