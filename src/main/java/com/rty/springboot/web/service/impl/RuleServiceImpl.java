package com.rty.springboot.web.service.impl;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.RuleInfoBean;
import com.rty.springboot.web.mapper.skb.RuleMapper;
import com.rty.springboot.web.service.IRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleServiceImpl implements IRuleService {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public List<RuleInfoBean> getRuleInfos(Map<String, String> param) {
        return ruleMapper.queryRuleInfos(param);
    }
}
