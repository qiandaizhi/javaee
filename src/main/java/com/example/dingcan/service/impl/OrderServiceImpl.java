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

        // 1. 验证订单是否存在
        if (order == null) {
            throw new Exception("订单不存在");
        }

        // 2.【健壮性修改】验证订单归属权，防止空指针
        if (userId == null || !userId.equals(order.getUserId())) {
            throw new Exception("无权操作此订单");
        }

        // 3. 验证订单状态是否允许取消
        //    只有“待处理”(PAID)状态的订单可以取消
        if (!"PAID".equals(order.getStatus())) {
            // 根据订单的当前状态给出更具体的提示
            switch (order.getStatus()) {
                case "DELIVERING":
                case "COMPLETED":
                    throw new Exception("订单已发货或已完成，无法取消");
                case "CANCELLED":
                    throw new Exception("订单已经是已取消状态");
                default:
                    throw new Exception("当前订单状态(" + order.getStatus() + ")无法取消");
            }
        }

        // 4. 更新数据库中的订单状态，并检查影响的行数
        int rowsAffected = orderMapper.updateStatus(orderId, "CANCELLED");

        // 5.【健壮性修改】如果更新影响的行数为0，说明更新失败，抛出异常
        if (rowsAffected == 0) {
            throw new Exception("更新订单状态失败，请稍后再试");
        }

        // 6. 更新返回给前端的Java对象的状态
        order.setStatus("CANCELLED");
        return order;
    }
}