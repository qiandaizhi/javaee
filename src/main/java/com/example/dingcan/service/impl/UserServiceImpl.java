package com.example.dingcan.service.impl;

import com.example.dingcan.mapper.UserMapper;
import com.example.dingcan.pojo.User;
import com.example.dingcan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 【重要】确保这里没有 abstract 关键字
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

    /**
     * 实现更新个人资料的逻辑
     */
    @Override
    @Transactional
    public User updateProfile(User userToUpdate) throws Exception {
        // 为了安全起见，我们只允许更新部分字段，比如手机号。
        // 创建一个新的User对象，只设置允许更新的字段，防止意外更新其他敏感信息。
        User user = new User();
        user.setId(userToUpdate.getId());
        user.setPhone(userToUpdate.getPhone());
        // 如果未来允许更新其他字段，在这里添加即可
        // user.setXXX(userToUpdate.getXXX());

        int rowsAffected = userMapper.update(user);

        if (rowsAffected == 0) {
            throw new Exception("用户信息更新失败，用户不存在或数据无变化");
        }

        // 从数据库重新获取最新的用户信息，以确保数据完整，但不包含密码
        User updatedUser = userMapper.findByUsername(userToUpdate.getUsername());
        if (updatedUser != null) {
            updatedUser.setPassword(null);
        }
        return updatedUser;
    }
}