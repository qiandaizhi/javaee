package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Review;

public interface ReviewMapper {
    /**
     * 插入一条新的评价
     * @param review 评价对象
     * @return 影响的行数
     */
    int insert(Review review);
}