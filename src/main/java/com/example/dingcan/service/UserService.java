package com.example.dingcan.service;

import com.example.dingcan.pojo.User;

public interface UserService {
    User register(String username, String password, String phone) throws Exception;
    User login(String username, String password);
    User updateProfile(User userToUpdate) throws Exception;
}