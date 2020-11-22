package com.rty.springboot.bean;

public class JobTaskInfo {
    private String id;
    private String jobId;
    private String analyzerStartTime;
    private String analyzerEndTime;
    private String language;
    private String tool;
    private String area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getAnalyzerStartTime() {
        return analyzerStartTime;
    }

    public void setAnalyzerStartTime(String analyzerStartTime) {
        this.analyzerStartTime = analyzerStartTime;
    }

    public String getAnalyzerEndTime() {
        return analyzerEndTime;
    }

    public void setAnalyzerEndTime(String analyzerEndTime) {
        this.analyzerEndTime = analyzerEndTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
