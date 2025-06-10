package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Order;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface OrderMapper {
    int insert(Order order);
    Order findById(@Param("id") Integer id);
    List<Order> findByUserId(@Param("userId") Integer userId);
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
    int deleteById(@Param("id") Integer id);

    /**
     * 【新增】查询所有用户的全部订单
     * @return 订单列表
     */
    List<Order> findAll();
}