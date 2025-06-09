package com.example.dingcan.controller;

import com.example.dingcan.pojo.Review;
import com.example.dingcan.pojo.User;
import com.example.dingcan.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 用于接收评价请求数据的DTO
    public static class ReviewRequest {
        public Integer orderId;
        public Integer dishId;
        public Integer rating;
        public String comment;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "用户未登录"));
        }

        try {
            Review review = new Review();
            review.setOrderId(request.orderId);
            review.setDishId(request.dishId);
            review.setRating(request.rating);
            review.setComment(request.comment);

            Review createdReview = reviewService.addReview(review, currentUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}