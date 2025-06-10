package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Order;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface OrderMapper {
    int insert(Order order);
    Order findById(@Param("id") Integer id);
    List<Order> findByUserId(@Param("userId") Integer userId);
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    // 我们在评价功能中其实没有用到这个方法，可以安全删除
    // int addEvaluation(Order order);

    /**
     * 根据ID删除订单
     * 【关键】确保参数前有 @Param 注解
     */
    int deleteById(@Param("id") Integer id);
}