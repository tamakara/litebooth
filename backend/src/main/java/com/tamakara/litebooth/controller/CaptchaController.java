package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.CaptchaDTO;
import com.tamakara.litebooth.domain.vo.captcha.CaptchaInfoVO;
import com.tamakara.litebooth.service.CaptchaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/verify")
    public ResponseEntity<String> verify(
            @RequestBody CaptchaDTO captchaDTO
    ) {
        String token = captchaService.verify(captchaDTO);
        return ResponseEntity.ok(token);
    }
}
