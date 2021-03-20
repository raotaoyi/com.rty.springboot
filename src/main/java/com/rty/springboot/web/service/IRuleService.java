package com.rty.springboot.web.service;

import com.rty.springboot.bean.RuleInfoBean;

import java.util.List;
import java.util.Map;

public interface IRuleService {
    /**
     * 获取job的信息
     */
    List<RuleInfoBean> getRuleInfos(Map<String, String> param);
}
