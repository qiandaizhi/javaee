package com.example.dingcan.service.impl;

import com.example.dingcan.mapper.OrderMapper;
import com.example.dingcan.mapper.ReviewMapper;
import com.example.dingcan.pojo.Order;
import com.example.dingcan.pojo.Review;
import com.example.dingcan.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public Review addReview(Review review, Integer userId) throws Exception {
        // 1. 数据校验
        if (review.getOrderId() == null || review.getDishId() == null) {
            throw new Exception("订单ID和菜品ID不能为空");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new Exception("评分必须在1-5星之间");
        }
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new Exception("评价内容不能为空");
        }

        // 2. 业务校验
        Order order = orderMapper.findById(review.getOrderId());
        if (order == null || !order.getUserId().equals(userId)) {
            throw new Exception("订单不存在或无权操作");
        }
        if (!"COMPLETED".equals(order.getStatus())) {
            throw new Exception("只有已完成的订单才能评价");
        }

        // 3. 设置用户ID并插入数据库
        review.setUserId(userId);
        int rowsAffected = reviewMapper.insert(review);
        if (rowsAffected == 0) {
            throw new Exception("提交评价失败，请重试");
        }

        return review;
    }
}