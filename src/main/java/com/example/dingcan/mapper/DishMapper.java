package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Dish;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DishMapper {
    List<Dish> selectAllOnSale();
    Dish findById(@Param("id") Integer id);
    List<Dish> selectOnSaleByNameContaining(@Param("name") String name);
}