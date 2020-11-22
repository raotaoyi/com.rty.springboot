package com.rty.springboot.web.mapper;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.JobTaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobInfoMapper {
    /**
     * 插入job信息
     */
    void insertJobInfo(@Param("jobInfoBeans") List<JobInfoBean> jobInfoBeans);

    /**
     * 插入job信息
     */
    void insertJobTimeDetail(@Param("jobInfoBeans") List<JobInfoBean> jobInfoBeans);

    /**
     * 插入task信息
     */
    void insertJobTask(@Param("jobTaskInfos") List<JobTaskInfo> jobTaskInfos);


}
