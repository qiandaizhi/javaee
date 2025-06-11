package com.example.dingcan.pojo;

import java.math.BigDecimal;
import java.util.List; // 引入List

public class Dish {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer status;

    // 【新增】一个列表，用于存放该菜品下的所有评价
    private List<Review> reviews;

    // --- Getters and Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    // 【新增】reviews字段的Getter和Setter
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}