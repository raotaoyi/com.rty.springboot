package com.rty.springboot.bean;

import java.util.List;

public class ProjectInfoBean {
    private String registerKey;
    private String projectName;
    private String createTime;
    private String area;
    private List<JobInfoBean> jobs;

    public String getRegisterKey() {
        return registerKey;
    }

    public void setRegisterKey(String registerKey) {
        this.registerKey = registerKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<JobInfoBean> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobInfoBean> jobs) {
        this.jobs = jobs;
    }
}
