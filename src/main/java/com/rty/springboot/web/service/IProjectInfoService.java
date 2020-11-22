package com.rty.springboot.web.service;

import com.rty.springboot.bean.ProjectInfoBean;

public interface IProjectInfoService {
    /**
     * 插入工程
     */
    void addProjectInfo(ProjectInfoBean projectInfoBean);
}
