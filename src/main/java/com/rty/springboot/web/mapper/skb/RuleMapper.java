package com.rty.springboot.web.mapper.skb;

import com.rty.springboot.bean.JobInfoBean;
import com.rty.springboot.bean.RuleInfoBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RuleMapper {
    /**
     * 获取job的信息
     */
    List<RuleInfoBean> queryRuleInfos(Map<String, String> param);
}
