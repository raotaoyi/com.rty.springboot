package com.rty.springboot.web.controller;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.ResultInfo;
import com.rty.springboot.util.Constant;
import com.rty.springboot.util.DateUtil;
import com.rty.springboot.util.ExcelFileUtil;
import com.rty.springboot.util.StringUtil;
import com.rty.springboot.web.service.IJobInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/gain/job")
public class JobController extends AbstractContorller {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private IJobInfoService jobInfoService;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/getJobs/{area}", method = RequestMethod.GET)
    public ResultInfo<?> getJobInfo(@PathVariable String area, HttpServletRequest request) {
        LOGGER.info("get count");
        try {
            ResultInfo resultInfo = createSuccessResult("get job count success");
            if (StringUtil.isEmpty(area)) {
                return createFailResult("get job count success");
            }
            Map<String, String> param = new HashMap<>();
            param.put("area", area);
            initParam(param, request);
            resultInfo.setData(jobInfoService.getJobInfos(param));
            return resultInfo;
        } catch (Exception e) {
            LOGGER.info("get job count fail", e);
            return createFailResult("get job count fail");
        }
    }

    /**
     * 导出查询的数据
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportJobInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> param = new HashMap<>();
            Map<String, Map<String, List<Object>>> jobInfoData = new HashMap<>();
            List<Object> jobList=new ArrayList<>();
            initParam(param, request);
            List<JobInfoBean> jobs = jobInfoService.getJobInfos(param);
            jobList.addAll(jobs);
            Map<String, List<Object>> dataMap = new HashMap<>();
            dataMap.put(env.getProperty("job.info.detail"), jobList);
            jobInfoData.put("jobDetail", dataMap);
            ExcelFileUtil.exportExcel(response, "jobDetail.xlsx", jobInfoData);
        } catch (Exception e) {
            LOGGER.error("export job info fail", e);
        }
    }

    public void initParam(Map<String, String> param, HttpServletRequest request) {
        String startTime = request.getParameter("startTime");
        param.put("startTime", StringUtil.isEmpty(startTime) ?
                DateUtil.getBeforeDay(DateUtil.getCurrentDay(), Constant.NEGATIVE_NUM_SEVEN) : startTime);
        String endTime = request.getParameter("endTime");
        param.put("endTime", StringUtil.isEmpty(endTime) ?
                DateUtil.getBeforeDay(DateUtil.getCurrentDay(), Constant.NEGATIVE_NUM_ONE) : endTime);
    }

}
