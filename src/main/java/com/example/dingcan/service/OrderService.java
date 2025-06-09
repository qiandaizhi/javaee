package com.example.dingcan.service;

import com.example.dingcan.pojo.Order;
import java.util.List;

public interface OrderService {

    /**
     * 创建一个新订单
     * @param order 包含所有订单信息的订单对象
     * @return 创建成功并带有ID的订单对象
     * @throws Exception 如果创建失败
     */
    Order createOrder(Order order) throws Exception;

    /**
     * 获取当前用户的所有订单
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getMyOrders(Integer userId);

    /**
     * 取消一个订单
     * @param orderId 订单ID
     * @param userId 用户ID（用于安全校验）
     * @return 更新状态后的订单对象
     * @throws Exception 如果取消失败或无权操作
     */
    Order cancelOrder(Integer orderId, Integer userId) throws Exception;

    /**
     * 【新增】为订单添加评价的方法声明
     * @param orderId 订单ID
     * @param userId 用户ID
     * @param text 评价内容
     * @param rating 评价星级
     * @return 更新后的订单对象
     * @throws Exception 如果评价失败
     */
    Order addOrderEvaluation(Integer orderId, Integer userId, String text, Integer rating) throws Exception;
}