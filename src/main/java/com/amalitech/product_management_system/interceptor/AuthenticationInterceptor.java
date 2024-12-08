package com.amalitech.product_management_system.interceptor;

import com.amalitech.product_management_system.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("A valid verification token is required");
            return false;
        }

        var token = header.substring(7);
        if (!jwtService.isValidToken(token)) {
            System.out.println("A valid verification token is required");
            return false;
        }

        System.out.println("Authentication interceptor passed");
        return true;
    }
}
