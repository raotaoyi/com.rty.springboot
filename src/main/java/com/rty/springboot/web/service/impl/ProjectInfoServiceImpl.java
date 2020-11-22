package com.rty.springboot.web.service.impl;

import com.rty.springboot.bean.ProjectInfoBean;
import com.rty.springboot.web.mapper.ProjectInfoMapper;
import com.rty.springboot.web.service.IProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectInfoServiceImpl implements IProjectInfoService {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;
    @Override
    public void addProjectInfo(ProjectInfoBean projectInfoBean) {
        projectInfoMapper.insertProjectInfo(projectInfoBean);

    }
}
