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

    // 为了让Spring管理，我们把它定义成一个Bean
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User register(String username, String password, String phone) throws Exception {
        if (userMapper.findByUsername(username) != null) {
            throw new Exception("用户名 '" + username + "' 已被注册！");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setPhone(phone);
        userMapper.insert(newUser);
        newUser.setPassword(null);
        return newUser;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(null);
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public User updateProfile(User userToUpdate) throws Exception {
        User user = new User();
        user.setId(userToUpdate.getId());
        user.setPhone(userToUpdate.getPhone());
        int rowsAffected = userMapper.update(user);
        if (rowsAffected == 0) {
            throw new Exception("用户信息更新失败，用户不存在或数据无变化");
        }
        User updatedUser = userMapper.findByUsername(userToUpdate.getUsername());
        if (updatedUser != null) {
            updatedUser.setPassword(null);
        }
        return updatedUser;
    }
}