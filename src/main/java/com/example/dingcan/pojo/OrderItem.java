package com.example.dingcan.pojo;

import java.math.BigDecimal;

public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer dishId;
    private Integer quantity;
    private BigDecimal price; // 下单时的单价

    // --- 本次新增的字段 ---
    // 这个字段不对应数据库列，用于在查询时方便地关联菜品信息
    private Dish dish;

    // --- Getters and Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    // --- 新增的Getter和Setter ---
    public Dish getDish() { return dish; }
    public void setDish(Dish dish) { this.dish = dish; }
}
