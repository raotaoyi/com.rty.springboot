package com.rty.springboot.web.mapper.mc;

import com.rty.springboot.bean.ProjectInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectInfoMapper {
    /**
     * 插入工程
     */
    void insertProjectInfo(@Param("projectInfo")ProjectInfoBean projectInfoBean);
}
