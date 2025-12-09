package com.tamakara.litebooth.service;

import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;

public interface AuthService {
    LoginVO login(LoginFormDTO loginFormDTO);
}
