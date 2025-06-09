package com.example.dingcan.service.impl;

import com.example.dingcan.mapper.OrderMapper;
import com.example.dingcan.mapper.OrderItemMapper;
import com.example.dingcan.pojo.Order;
import com.example.dingcan.pojo.OrderItem;
import com.example.dingcan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public Order createOrder(Order order) throws Exception {
        orderMapper.insert(order);
        List<OrderItem> items = order.getOrderItems();
        if (items == null || items.isEmpty()) {
            throw new Exception("订单项不能为空！");
        }
        for (OrderItem item : items) {
            item.setOrderId(order.getId());
        }
        orderItemMapper.batchInsert(items);
        return order;
    }

    // --- 本次新增的方法实现 ---
    @Override
    public List<Order> getMyOrders(Integer userId) {
        // 1. 查出该用户的所有订单主体
        List<Order> orders = orderMapper.findByUserId(userId);
        // 2. 循环遍历，为每个订单填充其详细订单项
        for (Order order : orders) {
            List<OrderItem> items = orderItemMapper.findByOrderId(order.getId());
            order.setOrderItems(items);
        }
        return orders;
    }

    @Override
    @Transactional
    public Order cancelOrder(Integer orderId, Integer userId) throws Exception {
        Order order = orderMapper.findById(orderId);
        // 安全校验：确认订单存在，且属于当前操作用户
        if (order == null || !order.getUserId().equals(userId)) {
            throw new Exception("订单不存在或无权操作");
        }
        // 业务逻辑校验：只有特定状态的订单可以取消
        if ("DELIVERING".equals(order.getStatus()) || "COMPLETED".equals(order.getStatus())) {
            throw new Exception("订单已发货或已完成，无法取消");
        }
        if ("CANCELLED".equals(order.getStatus())) {
            throw new Exception("订单已处于取消状态");
        }

        // 更新订单状态为“已取消”
        orderMapper.updateStatus(orderId, "CANCELLED");
        order.setStatus("CANCELLED");
        return order;
    }
}