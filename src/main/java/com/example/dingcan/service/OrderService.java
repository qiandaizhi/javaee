package com.example.dingcan.service;

import com.example.dingcan.pojo.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order) throws Exception;
    List<Order> getMyOrders(Integer userId);
    Order cancelOrder(Integer orderId, Integer userId) throws Exception;

    /**
     * 【新增】删除一个订单
     * @param orderId 订单ID
     * @param userId 用户ID (用于安全校验)
     * @throws Exception 如果删除失败
     */
    void deleteOrder(Integer orderId, Integer userId) throws Exception;
}