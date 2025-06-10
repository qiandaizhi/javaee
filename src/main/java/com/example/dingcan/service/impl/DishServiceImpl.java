package com.example.dingcan.service.impl;

import com.example.dingcan.mapper.DishMapper;
import com.example.dingcan.pojo.Dish;
import com.example.dingcan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public List<Dish> getAllDishesForDisplay() {
        return dishMapper.selectAllOnSale();
    }

    @Override
    public Dish getDishById(Integer dishId) {
        return dishMapper.findById(dishId);
    }

    @Override
    public List<Dish> searchDishes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return dishMapper.selectAllOnSale();
        }
        return dishMapper.selectOnSaleByNameContaining(keyword.trim());
    }
}