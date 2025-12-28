package com.tamakara.litebooth.service;

import com.tamakara.litebooth.domain.dto.auth.ChangePasswordDTO;
import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.auth.RefreshTokenDTO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;
import com.tamakara.litebooth.domain.vo.auth.RefreshTokenVO;

public interface AuthService {
    LoginVO login(LoginFormDTO loginFormDTO);

    RefreshTokenVO refreshToken(RefreshTokenDTO refreshTokenDTO);

    void changePassword(ChangePasswordDTO changePasswordDTO);
}
