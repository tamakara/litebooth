package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.domain.dto.captcha.CaptchaDTO;
import com.tamakara.litebooth.domain.vo.captcha.CaptchaInfoVO;
import com.tamakara.litebooth.service.CaptchaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户端验证码模块接口")
@RestController
@RequestMapping("/user/captcha")
@RequiredArgsConstructor
public class UserCaptchaController {
    private final CaptchaService captchaService;

    @GetMapping("/getCaptchaInfoVO")
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
