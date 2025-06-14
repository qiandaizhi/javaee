package com.example.dingcan.pojo;

import java.util.Date;

public class Review {
    private Integer id;
    private Integer orderId;
    private Integer userId;
    private Integer dishId;
    private Integer rating;
    private String comment;
    private Date createTime;

    // 【新增】一个不对应数据库字段的属性，用于在查询时存放评价者的用户名
    private String username;

    // --- Getters and Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    // 【新增】username字段的Getter和Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}