package com.rty.springboot.web.controller;

import com.rty.springboot.bean.ProjectInfoBean;
import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.web.service.IJobInfoService;
import com.rty.springboot.web.service.IProjectInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 类说明:同步数据的接口
 */

@RestController
@RequestMapping("/v1/gain")
public class DataController extends AbstractContorller {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private IProjectInfoService projectInfoService;

    @Autowired
    private IJobInfoService jobInfoService;

    @RequestMapping(value = "/projectInfo", method = RequestMethod.POST)
    public ResultInfo<?> syncData(@RequestBody ProjectInfoBean projectInfoBean, HttpServletRequest request) {
        LOGGER.info("v1/gain/projectInfo");
        try {
            ResultInfo resultInfo = createSuccessResult();
            //proj into
            String registerKey = UUID.randomUUID().toString();
            projectInfoBean.setProjectName(projectInfoBean.getProjectName() + "_" + registerKey);
            projectInfoBean.setRegisterKey(registerKey);
            projectInfoBean.setCreateTime("");
            projectInfoBean.setArea("GREEN");
            //job info
            String jobId = UUID.randomUUID().toString();
            projectInfoBean.getJobs().get(0).setRegisterKey(registerKey);
            projectInfoBean.getJobs().get(0).setId(jobId);
            projectInfoBean.getJobs().get(0).setJobStatus("completed");
            projectInfoBean.getJobs().get(0).setTools("CodeMars");
            projectInfoBean.getJobs().get(0).setJobDate("");
            projectInfoBean.getJobs().get(0).setArea("GREEN");
            projectInfoBean.getJobs().get(0).setStartTime("");
            projectInfoBean.getJobs().get(0).setCreateTime("");
            projectInfoBean.getJobs().get(0).setEndTime("");
            //task info
            String taskId = UUID.randomUUID().toString();
            projectInfoBean.getJobs().get(0).getTasks().get(0).setId(taskId);
            projectInfoBean.getJobs().get(0).getTasks().get(0).setJobId(jobId);
            projectInfoBean.getJobs().get(0).getTasks().get(0).setLanguage("java");
            projectInfoBean.getJobs().get(0).getTasks().get(0).setTool("CodeMars");
            projectInfoBean.getJobs().get(0).getTasks().get(0).setAnalyzerStartTime("");
            projectInfoBean.getJobs().get(0).getTasks().get(0).setAnalyzerEndTime("");
            projectInfoBean.getJobs().get(0).getTasks().get(0).setArea("GREEN");
            projectInfoService.addProjectInfo(projectInfoBean);
            jobInfoService.addJobInfo(projectInfoBean.getJobs());
            jobInfoService.addJobTask(projectInfoBean.getJobs().get(0).getTasks());
            return resultInfo;
        } catch (Exception e) {
            LOGGER.info("gain project info fail", e);
            return createFailResult("gain project info fail");
        }
    }
}
