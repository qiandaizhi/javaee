package com.example.dingcan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.dingcan.mapper") // 告诉MyBatis去哪里扫描Mapper接口
public class DingcanApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingcanApplication.class, args);
        System.out.println("====== 订餐系统 Spring Boot 启动成功! ======");
    }

}