package com.example.dingcan.controller;

import com.example.dingcan.pojo.User;
import com.example.dingcan.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 定义一个简单的DTO用于接收注册和登录的请求体
    public static class UserCredentials {
        public String username;
        public String password;
        public String phone;
    }

    // 【新增】一个简单的DTO，用于接收个人资料更新的请求体
    public static class ProfileUpdateRequest {
        public String phone;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCredentials credentials) {
        try {
            User registeredUser = userService.register(credentials.username, credentials.password, credentials.phone);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            // 返回一个包含错误信息的JSON对象
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentials credentials, HttpSession session) {
        User user = userService.login(credentials.username, credentials.password);
        if (user != null) {
            // 登录成功，将用户信息存入Session
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "用户名或密码错误"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "用户未登录"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate(); // 销毁Session
        return ResponseEntity.ok(Collections.singletonMap("message", "退出成功"));
    }

    /**
     * 【新增】处理更新个人资料的API接口
     * 使用 @PutMapping 来表示这是一个更新操作
     */
    @PutMapping("/me/update")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest request, HttpSession session) {
        // 1. 从Session获取当前登录的用户
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录，无法更新"));
        }

        try {
            // 2. 将要更新的信息（手机号）设置到从Session取出的用户对象上
            currentUser.setPhone(request.phone);

            // 3. 调用Service层执行更新操作
            User updatedUser = userService.updateProfile(currentUser);

            // 4. 【非常重要】将Service层返回的、最新的用户信息重新存入Session
            //    这样可以确保Session中的数据与数据库保持同步
            session.setAttribute("loggedInUser", updatedUser);

            // 5. 返回成功响应，并将最新的用户信息作为JSON返回给前端
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            // 6. 如果过程中出现任何异常，返回失败信息
            e.printStackTrace(); // 在服务器控制台打印错误，便于调试
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}