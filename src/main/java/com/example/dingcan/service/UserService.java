package com.example.dingcan.service;

import com.example.dingcan.pojo.User;

public interface UserService {
    /**
     * 注册新用户
     * @param username 用户名
     * @param password 原始密码
     * @param phone 手机号
     * @return 注册成功的用户对象
     * @throws Exception 如果用户名已存在
     */
    User register(String username, String password, String phone) throws Exception;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 原始密码
     * @return 登录成功的用户对象（不含密码），如果失败则返回null
     */
    User login(String username, String password);
}