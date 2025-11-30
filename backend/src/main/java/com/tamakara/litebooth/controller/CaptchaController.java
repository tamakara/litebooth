package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.vo.captcha.CaptchaInfoVO;
import com.tamakara.litebooth.service.CaptchaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "验证码接口")
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {
    private final CaptchaService captchaService;

    @GetMapping("/vo/CaptchaInfoVO")
    public ResponseEntity<CaptchaInfoVO> getCaptchaInfoVO() {
        CaptchaInfoVO vo = captchaService.getCaptchaInfoVO();
        return ResponseEntity.ok(vo);
    }
}
