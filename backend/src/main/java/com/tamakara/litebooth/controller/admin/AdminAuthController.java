package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.auth.RefreshTokenDTO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;
import com.tamakara.litebooth.domain.vo.auth.RefreshTokenVO;
import com.tamakara.litebooth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端认证模块接口")
@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginVO> login(
            @RequestBody LoginFormDTO loginFormDTO
    ) {
        LoginVO vo = authService.login(loginFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenVO> refreshToken(
            @RequestBody RefreshTokenDTO refreshTokenDTO
    ) {
        RefreshTokenVO vo = authService.refreshToken(refreshTokenDTO);
        return ResponseEntity.ok(vo);
    }
}
