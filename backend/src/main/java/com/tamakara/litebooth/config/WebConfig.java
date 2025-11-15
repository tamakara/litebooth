package com.tamakara.litebooth.config;

import com.tamakara.litebooth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                String authorizationHeader = request.getHeader("Authorization");
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring(7);
                    try {
                        Long userId = JwtUtil.decodeJWT(token, TOKEN_SECRET);
                        request.setAttribute("userId", userId);
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        try {
                            response.getWriter().write("{\"error\": \"Token expired or invalid\"}");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        return false;
                    }
                }
                return true;
            }
        }).addPathPatterns("/**");
    }
}
