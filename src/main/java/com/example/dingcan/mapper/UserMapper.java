// UserMapper.java
package com.example.dingcan.mapper;

import com.example.dingcan.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    int insert(User user);

    /**
     * 【新增】根据用户ID更新用户信息
     * @param user 包含ID和需要更新字段的用户对象
     * @return 影响的行数
     */
    int update(User user);
}