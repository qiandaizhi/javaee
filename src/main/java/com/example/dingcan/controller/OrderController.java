package com.example.dingcan.controller;

import com.example.dingcan.pojo.Dish;
import com.example.dingcan.pojo.Order;
import com.example.dingcan.pojo.OrderItem;
import com.example.dingcan.pojo.User;
import com.example.dingcan.service.DishService;
import com.example.dingcan.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    // 定义一个DTO(Data Transfer Object)来接收前端的下单请求数据
    public static class OrderRequest {
        public List<CartItem> cartItems;
        public String address;
        public String phone;

        public static class CartItem {
            public Integer dishId;
            public Integer quantity;
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, HttpSession session) {
        // 1. 检查用户是否登录
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录，请先登录"));
        }

        // 2. 验证购物车是否为空
        if (orderRequest.cartItems == null || orderRequest.cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "购物车不能为空"));
        }

        // 3. 构建Order和OrderItem对象
        Order order = new Order();
        order.setUserId(currentUser.getId());
        order.setAddress(orderRequest.address);
        order.setPhone(orderRequest.phone);
        order.setStatus("PAID"); // 模拟支付成功，状态为待处理

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 4. 循环校验菜品，计算总价
        for (OrderRequest.CartItem cartItem : orderRequest.cartItems) {
            Dish dish = dishService.getDishById(cartItem.dishId);
            if (dish == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "菜品ID " + cartItem.dishId + " 不存在"));
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setDishId(cartItem.dishId);
            orderItem.setQuantity(cartItem.quantity);
            orderItem.setPrice(dish.getPrice()); // 使用数据库中的实时价格
            orderItems.add(orderItem);

            totalPrice = totalPrice.add(dish.getPrice().multiply(new BigDecimal(cartItem.quantity)));
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        // 5. 调用Service层创建订单
        try {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "创建订单失败：" + e.getMessage()));
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        List<Order> orders = orderService.getMyOrders(currentUser.getId());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        try {
            Order cancelledOrder = orderService.cancelOrder(orderId, currentUser.getId());
            return ResponseEntity.ok(cancelledOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}