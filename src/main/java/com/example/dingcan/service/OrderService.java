package com.example.dingcan.service;

import com.example.dingcan.pojo.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order) throws Exception;
    List<Order> getMyOrders(Integer userId);
    Order cancelOrder(Integer orderId, Integer userId) throws Exception;
    void deleteOrder(Integer orderId, Integer userId) throws Exception;
}