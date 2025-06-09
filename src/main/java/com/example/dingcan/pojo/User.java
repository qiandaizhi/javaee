package com.example.dingcan.pojo;

import java.util.Date; // 引入 java.util.Date

public class User {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private Date createTime; // 将类型从 LocalDateTime 修改为 Date

    // --- Getters 和 Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Date getCreateTime() { return createTime; } // 返回类型修改为 Date
    public void setCreateTime(Date createTime) { this.createTime = createTime; } // 参数类型修改为 Date
}