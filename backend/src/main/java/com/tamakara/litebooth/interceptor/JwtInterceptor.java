package com.tamakara.litebooth.interceptor;

import com.tamakara.litebooth.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final TokenUtil tokenUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        }

        String token = auth.substring(7);

        try {
            tokenUtil.validateAccessToken(token);
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
