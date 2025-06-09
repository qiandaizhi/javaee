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

    // 新增的方法实现
    @Override
    public Dish getDishById(Integer dishId) {
        return dishMapper.findById(dishId);
    }
}
