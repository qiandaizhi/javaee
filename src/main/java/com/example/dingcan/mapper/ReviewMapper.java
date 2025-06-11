package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Review;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ReviewMapper {
    int insert(Review review);

    /**
     * 【新增】根据菜品ID查询所有评价
     * @param dishId 菜品ID
     * @return 评价列表
     */
    List<Review> findByDishId(@Param("dishId") Integer dishId);
}