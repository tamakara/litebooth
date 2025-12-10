package com.tamakara.litebooth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.auth.RefreshTokenDTO;
import com.tamakara.litebooth.domain.entity.AdminInfo;
import com.tamakara.litebooth.domain.vo.auth.AdminInfoVO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;
import com.tamakara.litebooth.domain.vo.auth.RefreshTokenVO;
import com.tamakara.litebooth.mapper.AdminInfoMapper;
import com.tamakara.litebooth.service.AuthService;
import com.tamakara.litebooth.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final FileService fileService;
    private final AdminInfoMapper adminInfoMapper;
    private final StringRedisTemplate redisTemplate;

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Value("${token.access-token-expires}")
    private Integer ACCESS_TOKEN_EXPIRES;

    @Value("${token.refresh-token-expires}")
    private Integer REFRESH_TOKEN_EXPIRES;

    @Value("${minio.url-expires}")
    private Integer MINIO_URL_EXPIRES;

    @Override
    public LoginVO login(LoginFormDTO loginFormDTO) {
        AdminInfo adminInfo = adminInfoMapper.selectById(1L);

        String avatarUrl = fileService.getFileUrl(adminInfo.getAvatar(), MINIO_URL_EXPIRES);

        String accessToken = JWT
                .create()
                .withExpiresAt(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRES))
                .withClaim("jti", UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256(TOKEN_SECRET));

        String refreshToken = UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForSet().add(
                "refresh:admin",
                refreshToken
        );
        redisTemplate.expire("refresh:admin", REFRESH_TOKEN_EXPIRES, TimeUnit.SECONDS);

        AdminInfoVO adminInfoVO = new AdminInfoVO();
        adminInfoVO.setAvatar(avatarUrl);
        adminInfoVO.setUsername(adminInfo.getUsername());
        adminInfoVO.setNickname(adminInfo.getNickname());
        adminInfoVO.setAccessToken(accessToken);
        adminInfoVO.setRefreshToken(refreshToken);
        adminInfoVO.setExpires(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRES));

        return new LoginVO(true, adminInfoVO);
    }

    @Override
    public RefreshTokenVO refreshToken(RefreshTokenDTO refreshTokenDTO) {
        Boolean exist = redisTemplate.opsForSet()
                .isMember("refresh:admin", refreshTokenDTO.getRefreshToken());

        if (exist == null || !exist) {
            throw new RuntimeException("refreshToken 已失效");
        }

        String accessToken = JWT
                .create()
                .withExpiresAt(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRES))
                .withClaim("jti", UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256(TOKEN_SECRET));

        RefreshTokenVO vo = new RefreshTokenVO();
        vo.setAccessToken(accessToken);
        vo.setRefreshToken(refreshTokenDTO.getRefreshToken());
        vo.setExpires(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRES));

        return vo;
    }
}
