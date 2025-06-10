package com.example.dingcan.service;

import com.example.dingcan.pojo.Dish;
import java.util.List;

public interface DishService {
    List<Dish> getAllDishesForDisplay();
    Dish getDishById(Integer dishId);
    List<Dish> searchDishes(String keyword);
}