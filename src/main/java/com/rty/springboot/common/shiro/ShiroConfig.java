package com.rty.springboot.common.shiro;


import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/*@Configuration*/
public class ShiroConfig {

    //权限管理，配置主要是Realm的管理认证
    @Bean("secutiryManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(new MyRealm());

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    //配置ShiroFilterFactoryBean
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactory(@Qualifier("secutiryManager") SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        /*
         *从项目启动的时候可以看出加载自定义的shiro，当访问的时候，tomcat会遍历所有的filters，然后依次doFilter
         * */
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("myfilter", new MyFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainMap = new HashMap<>();
        filterChainMap.put("/**", "myfilter");
        filterChainMap.put("/login","anon");
        filterChainMap.put("/auth/token","anon");
        filterChainMap.put("/401","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }


}
