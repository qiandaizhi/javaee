package com.example.dingcan.service;

import com.example.dingcan.pojo.Review;

public interface ReviewService {
    /**
     * 添加一条新的菜品评价
     * @param review 包含评价信息的对象
     * @param userId 操作用户的ID，用于安全校验
     * @return 带有ID的新创建的Review对象
     * @throws Exception 如果校验失败或插入失败
     */
    Review addReview(Review review, Integer userId) throws Exception;
}