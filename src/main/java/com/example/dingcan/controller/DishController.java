// DishController.java

package com.example.dingcan.controller;

import com.example.dingcan.pojo.Dish;
import com.example.dingcan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // 引入 @RequestParam
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    public List<Dish> listDishes() {
        return dishService.getAllDishesForDisplay();
    }

    /**
     * 【新增】处理菜品搜索请求的API接口
     * @param keyword 前端传来的搜索关键词
     * @return 过滤后的菜品列表JSON数据
     */
    @GetMapping("/search")
    public List<Dish> searchDishes(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return dishService.searchDishes(keyword);
    }
}