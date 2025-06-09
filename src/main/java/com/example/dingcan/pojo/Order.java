package com.example.dingcan.pojo;

import java.math.BigDecimal;
import java.util.Date; // 引入 java.util.Date
import java.util.List;

public class Order {
    private Integer id;
    private Integer userId;
    private BigDecimal totalPrice;
    private String address;
    private String phone;
    private String status;
    private String shippingCompany;
    private String shippingCode;
    private Date createTime; // 将类型从 LocalDateTime 修改为 Date

    private List<OrderItem> orderItems;

    // --- Getters and Setters (createTime部分已修改) ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getShippingCompany() { return shippingCompany; }
    public void setShippingCompany(String shippingCompany) { this.shippingCompany = shippingCompany; }
    public String getShippingCode() { return shippingCode; }
    public void setShippingCode(String shippingCode) { this.shippingCode = shippingCode; }
    public Date getCreateTime() { return createTime; } // 返回类型修改为 Date
    public void setCreateTime(Date createTime) { this.createTime = createTime; } // 参数类型修改为 Date
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
}