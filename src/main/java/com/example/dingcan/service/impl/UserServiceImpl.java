package com.example.dingcan.service.impl;

import com.example.dingcan.mapper.UserMapper;
import com.example.dingcan.pojo.User;
import com.example.dingcan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 不再需要，可以删除
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 【修改一】不再需要密码编码器，删除或注释掉这一行
    // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public User register(String username, String password, String phone) throws Exception {
        if (userMapper.findByUsername(username) != null) {
            throw new Exception("用户名 '" + username + "' 已被注册！");
        }

        // 【修改二】删除密码加密的步骤
        // String encodedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setUsername(username);
        // 直接将原始密码存入对象
        newUser.setPassword(password);
        newUser.setPhone(phone);
        userMapper.insert(newUser);

        newUser.setPassword(null);
        return newUser;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);

        if (user == null) {
            return null;
        }

        // 【修改三】将密码验证方式从加密匹配改为简单的字符串相等判断
        // 原来的加密验证: if (passwordEncoder.matches(password, user.getPassword()))
        if (password.equals(user.getPassword())) {
            // 密码正确，返回用户信息
            user.setPassword(null);
            return user;
        }

        return null; // 密码错误
    }

    // updateProfile 方法保持不变
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