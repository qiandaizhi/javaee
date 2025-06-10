package com.example.dingcan.mapper;

import com.example.dingcan.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    int insert(User user);
    int update(User user);
}