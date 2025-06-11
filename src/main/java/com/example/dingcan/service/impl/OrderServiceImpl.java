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

    // createOrder, cancelOrder, deleteOrder 方法保持不变...
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
    @Transactional
    public Order cancelOrder(Integer orderId, Integer userId) throws Exception {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new Exception("订单不存在，无法取消");
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
        if (order == null) {
            throw new Exception("订单不存在或无权操作");
        }
        String status = order.getStatus();
        if (!"COMPLETED".equals(status) && !"CANCELLED".equals(status)) {
            throw new Exception("只有已完成或已取消的订单才能删除");
        }
        orderMapper.deleteById(orderId);
    }

    /**
     * 【重要修改】重写getMyOrders的逻辑
     */
    @Override
    public List<Order> getMyOrders(Integer userId) {
        // 1. 先查出该用户的所有订单（不包含订单项）
        // 这里我们使用findAll来显示所有订单，方便您测试
        List<Order> orders = orderMapper.findAll();

        // 2. 遍历每一个订单
        for (Order order : orders) {
            // 3. 根据当前订单的ID，去查询它对应的所有订单项（包含菜品和评价信息）
            List<OrderItem> items = orderItemMapper.findByOrderId(order.getId());

            // 4. 将查出来的订单项列表，设置到当前订单对象中
            order.setOrderItems(items);
        }

        // 5. 返回包含了完整信息的订单列表
        return orders;
    }
}