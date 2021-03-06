package com.rty.springboot.web.mapper.mc;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.JobTaskInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Mapper
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

    /**
     * 统计指定条件下的job数量
     */
    int queryJobCount(@Param("param") Map<String, String> param);

    /**
     * 获取job的信息
     */
    List<JobInfoBean> queryJobInfos(Map<String, String> param);
}
