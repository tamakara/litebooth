package com.tamakara.litebooth.config;

import com.tamakara.litebooth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String authorizationHeader = request.getHeader("Authorization");
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring(7);  // 提取token
                    Long userId = JwtUtil.decodeJWT(token, TOKEN_SECRET);
                    request.setAttribute("userId", userId);
                }
                return true;
            }
        }).addPathPatterns("/**");
    }
}