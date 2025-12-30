package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.auth.ChangePasswordDTO;
import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.auth.RefreshTokenDTO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;
import com.tamakara.litebooth.domain.vo.auth.RefreshTokenVO;
import com.tamakara.litebooth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端认证模块接口")
@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(
            @RequestBody @Valid LoginFormDTO loginFormDTO
    ) {
        LoginVO vo = authService.login(loginFormDTO);
        return Result.success(vo);
    }

    @PostMapping("/refresh-token")
    public Result<RefreshTokenVO> refreshToken(
            @RequestBody @Valid RefreshTokenDTO refreshTokenDTO
    ) {
        RefreshTokenVO vo = authService.refreshToken(refreshTokenDTO);
        return Result.success(vo);
    }

    @PutMapping("/password")
    public Result<Void> changePassword(
            @RequestBody @Valid ChangePasswordDTO changePasswordDTO
    ) {
        authService.changePassword(changePasswordDTO);
        return Result.success();
    }

}
