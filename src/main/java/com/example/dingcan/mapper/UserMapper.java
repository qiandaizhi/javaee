package com.example.dingcan.mapper;

import com.example.dingcan.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象，如果不存在则返回null
     */
    User findByUsername(@Param("username") String username);

    /**
     * 插入一个新用户
     * @param user 用户对象
     * @return 影响的行数
     */
    int insert(User user);
}