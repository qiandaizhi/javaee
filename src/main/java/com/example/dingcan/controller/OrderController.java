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

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

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
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        try {
            Order order = new Order();
            order.setUserId(currentUser.getId());
            order.setAddress(orderRequest.address);
            order.setPhone(orderRequest.phone);
            order.setStatus("PAID");
            List<OrderItem> orderItems = new ArrayList<>();
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderRequest.CartItem cartItem : orderRequest.cartItems) {
                Dish dish = dishService.getDishById(cartItem.dishId);
                if (dish == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "菜品ID " + cartItem.dishId + " 不存在"));
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setDishId(cartItem.dishId);
                orderItem.setQuantity(cartItem.quantity);
                orderItem.setPrice(dish.getPrice());
                orderItems.add(orderItem);
                totalPrice = totalPrice.add(dish.getPrice().multiply(new BigDecimal(cartItem.quantity)));
            }
            order.setTotalPrice(totalPrice);
            order.setOrderItems(orderItems);
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "创建订单时发生错误: " + e.getMessage()));
        }
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        try {
            List<Order> orders = orderService.getMyOrders(currentUser.getId());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "获取订单列表失败: " + e.getMessage()));
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        try {
            Order cancelledOrder = orderService.cancelOrder(orderId, currentUser.getId());
            return ResponseEntity.ok(cancelledOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }
        try {
            orderService.deleteOrder(orderId, currentUser.getId());
            return ResponseEntity.ok(Map.of("message", "订单删除成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}