package com.example.dingcan.mapper;

import com.example.dingcan.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface OrderItemMapper {
    int batchInsert(@Param("items") List<OrderItem> items);
    List<OrderItem> findByOrderId(@Param("orderId") Integer orderId);
}