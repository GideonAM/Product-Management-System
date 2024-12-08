package com.amalitech.product_management_system.config;

import com.amalitech.product_management_system.interceptor.AuthenticationInterceptor;
import com.amalitech.product_management_system.interceptor.LogInterceptor;
import com.amalitech.product_management_system.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InterceptorsConfig implements WebMvcConfigurer {

    private final JwtService jwtService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new AuthenticationInterceptor(jwtService))
                .addPathPatterns(List.of("/api/v1/categories/**", "/api/v1/orders", "/api/v1/products/**"))
                .excludePathPatterns("/api/v1/users/**");
    }
}
