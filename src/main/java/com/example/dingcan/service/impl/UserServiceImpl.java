package com.example.dingcan.service.impl;

import com.example.dingcan.mapper.UserMapper;
import com.example.dingcan.pojo.User;
import com.example.dingcan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 创建一个密码编码器Bean，实际项目中应通过@Bean配置
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User register(String username, String password, String phone) throws Exception {
        // 1. 检查用户名是否已存在
        if (userMapper.findByUsername(username) != null) {
            throw new Exception("用户名 '" + username + "' 已被注册！");
        }

        // 2. 对密码进行加密
        String encodedPassword = passwordEncoder.encode(password);

        // 3. 创建用户对象并保存到数据库
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setPhone(phone);
        userMapper.insert(newUser);

        // 4. 返回新创建的用户（不包含密码）
        newUser.setPassword(null);
        return newUser;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);

        // 1. 检查用户是否存在
        if (user == null) {
            return null; // 用户不存在
        }

        // 2. 验证密码
        if (passwordEncoder.matches(password, user.getPassword())) {
            // 密码正确，返回用户信息（不含密码）
            user.setPassword(null);
            return user;
        }

        return null; // 密码错误
    }
}
