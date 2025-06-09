// DishMapper.java

package com.example.dingcan.mapper;

import com.example.dingcan.pojo.Dish;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DishMapper {
    List<Dish> selectAllOnSale();

    Dish findById(@Param("id") Integer id);

    /**
     * 【新增】根据菜品名称模糊搜索在售的菜品
     * @param name 搜索关键词
     * @return 匹配的菜品列表
     */
    List<Dish> selectOnSaleByNameContaining(@Param("name") String name);
}