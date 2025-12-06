package com.tamakara.litebooth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.tamakara.litebooth.domain.dto.CaptchaDTO;
import com.tamakara.litebooth.domain.vo.captcha.CaptchaInfoVO;
import com.tamakara.litebooth.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final StringRedisTemplate redisTemplate;

    private static final int WIDTH = 110;
    private static final int HEIGHT = 36;
    private static final int LENGTH = 4;
    private static final long EXPIRE_SECONDS = 300;

    @Override
    public CaptchaInfoVO getCaptchaInfoVO() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(WIDTH, HEIGHT, LENGTH, LENGTH * 25);
        String code = captcha.getCode();
        String captchaKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                "captcha:key:" + captchaKey,
                code,
                EXPIRE_SECONDS,
                TimeUnit.SECONDS
        );
        String imgBase64 = captcha.getImageBase64();
        return new CaptchaInfoVO(captchaKey, imgBase64);
    }

    @Override
    public String verify(CaptchaDTO captchaDTO) {
        String code = redisTemplate.opsForValue().get("captcha:key:" + captchaDTO.getCaptchaKey());

        if (code != null && code.equalsIgnoreCase(captchaDTO.getCaptchaCode())) {
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(
                    "captcha:token:" + token,
                    captchaDTO.getCaptchaKey(),
                    EXPIRE_SECONDS,
                    TimeUnit.SECONDS
            );
            return token;
        }

        return null;
    }


}
