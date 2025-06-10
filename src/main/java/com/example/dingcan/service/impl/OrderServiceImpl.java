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
import java.util.Objects;

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
        if (order.getId() == null) {
            throw new Exception("创建订单失败，无法获取订单ID");
        }
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

    @Override
    public List<Order> getMyOrders(Integer userId) {
        return orderMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order cancelOrder(Integer orderId, Integer userId) throws Exception {
        if (orderId == null || userId == null) {
            throw new IllegalArgumentException("订单ID或用户ID不能为空");
        }
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new Exception("订单不存在，无法取消");
        }
        if (!Objects.equals(userId, order.getUserId())) {
            throw new Exception("无权操作不属于自己的订单");
        }
        String currentStatus = order.getStatus();
        if (!"PAID".equals(currentStatus)) {
            throw new Exception("只有“待处理”状态的订单才能取消");
        }
        int rowsAffected = orderMapper.updateStatus(orderId, "CANCELLED");
        if (rowsAffected == 0) {
            throw new Exception("取消订单失败，请刷新后重试");
        }
        order.setStatus("CANCELLED");
        return order;
    }

    @Override
    @Transactional
    public void deleteOrder(Integer orderId, Integer userId) throws Exception {
        Order order = orderMapper.findById(orderId);
        if (order == null || !Objects.equals(userId, order.getUserId())) {
            throw new Exception("订单不存在或无权操作");
        }
        String status = order.getStatus();
        if (!"COMPLETED".equals(status) && !"CANCELLED".equals(status)) {
            throw new Exception("只有已完成或已取消的订单才能删除");
        }
        orderMapper.deleteById(orderId);
    }
}