package com.tamakara.litebooth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TokenUtil {

    private final StringRedisTemplate redisTemplate;

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Value("${token.access-token-expires}")
    private Integer ACCESS_TOKEN_EXPIRES;

    @Value("${token.refresh-token-expires}")
    private Integer REFRESH_TOKEN_EXPIRES;

    public String createAccessToken() {
        return JWT.create()
                .withExpiresAt(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRES))
                .withClaim("jti", UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    public String createRefreshToken() {
        String refreshToken = UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForSet().add(
                "refresh:admin",
                refreshToken
        );
        redisTemplate.expire("refresh:admin", REFRESH_TOKEN_EXPIRES, TimeUnit.SECONDS);
        return refreshToken;
    }

    public boolean validateRefreshToken(String refreshToken) {
        Boolean exist = redisTemplate.opsForSet()
                .isMember("refresh:admin", refreshToken);
        return exist != null && exist;
    }

    public void validateAccessToken(String token) {
        JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
    }

    public Integer getAccessTokenExpires() {
        return ACCESS_TOKEN_EXPIRES;
    }
}
