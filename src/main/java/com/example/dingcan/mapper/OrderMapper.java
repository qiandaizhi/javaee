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
}