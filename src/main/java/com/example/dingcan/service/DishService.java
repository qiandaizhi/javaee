package com.example.dingcan.service;

import com.example.dingcan.pojo.Dish;
import java.util.List;

public interface DishService {
    /**
     * 获取所有可供展示的菜品
     * @return 菜品列表
     */
    List<Dish> getAllDishesForDisplay();

    /**
     * 根据ID获取菜品详情
     * @param dishId 菜品ID
     * @return 菜品对象
     */
    Dish getDishById(Integer dishId); // 新增的方法
}