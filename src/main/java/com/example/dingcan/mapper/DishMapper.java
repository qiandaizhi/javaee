package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Dish;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DishMapper {
    /**
     * 查询所有在售的菜品
     * @return 菜品列表
     */
    List<Dish> selectAllOnSale();

    /**
     * 根据ID查询菜品
     * @param id 菜品ID
     * @return 菜品对象
     */
    Dish findById(@Param("id") Integer id); // 新增的方法
}