package com.example.dingcan.service;

import com.example.dingcan.pojo.Dish;
import java.util.List;

public interface DishService {
    List<Dish> getAllDishesForDisplay();
    Dish getDishById(Integer dishId);

    /**
     * 【新增】根据关键词搜索菜品
     * @param keyword 搜索关键词
     * @return 匹配的菜品列表
     */
    List<Dish> searchDishes(String keyword);
}