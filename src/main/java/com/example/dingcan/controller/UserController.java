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

    public static class UserCredentials {
        public String username;
        public String password;
        public String phone;
    }

    public static class ProfileUpdateRequest {
        public String phone;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCredentials credentials) {
        try {
            User registeredUser = userService.register(credentials.username, credentials.password, credentials.phone);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentials credentials, HttpSession session) {
        User user = userService.login(credentials.username, credentials.password);
        if (user != null) {
            // 手动将用户信息存入Session
            session.setAttribute("loggedInUser", user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "用户名或密码错误"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        // 从Session获取当前用户
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "用户未登录"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        // 销毁Session
        session.invalidate();
        return ResponseEntity.ok(Collections.singletonMap("message", "退出成功"));
    }

    @PutMapping("/me/update")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest request, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        try {
            currentUser.setPhone(request.phone);
            User updatedUser = userService.updateProfile(currentUser);
            // 更新Session中的用户信息
            session.setAttribute("loggedInUser", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}