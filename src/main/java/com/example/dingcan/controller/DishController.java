package com.example.dingcan.controller;

import com.example.dingcan.pojo.Dish;
import com.example.dingcan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController 注解相当于 @Controller 和 @ResponseBody 的组合
// 它表示这个类中的所有方法都将直接返回JSON数据
@RestController
@RequestMapping("/api/dishes") // 给所有API加上统一前缀 /api
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 获取所有菜品列表的API接口
     * @return 菜品列表的JSON数据
     */
    @GetMapping("/list")
    public List<Dish> listDishes() {
        return dishService.getAllDishesForDisplay();
    }
}
