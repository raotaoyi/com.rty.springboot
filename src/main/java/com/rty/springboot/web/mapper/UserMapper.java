package com.rty.springboot.web.mapper;

import com.rty.springboot.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserInfo getUser(@Param("username") String username);
}
