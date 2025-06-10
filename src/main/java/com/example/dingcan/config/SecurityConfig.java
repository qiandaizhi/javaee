package com.example.dingcan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF保护
                .csrf(AbstractHttpConfigurer::disable)

                // 配置URL的授权规则
                .authorizeHttpRequests(authz -> authz
                        // 【重要修改】允许所有人访问首页和静态资源
                        .requestMatchers("/", "/index.html", "/*.js", "/*.css", "/*.ico").permitAll()

                        // 允许对登录、注册、查看菜品等API的匿名访问
                        .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                        .requestMatchers("/api/dishes/list", "/api/dishes/search").permitAll()

                        // 其他所有请求都需要用户通过身份验证
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}