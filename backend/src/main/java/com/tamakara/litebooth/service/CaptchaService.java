package com.tamakara.litebooth.service;

import com.tamakara.litebooth.domain.dto.captcha.CaptchaDTO;
import com.tamakara.litebooth.domain.vo.captcha.CaptchaInfoVO;
public interface CaptchaService {
    CaptchaInfoVO getCaptchaInfoVO();

    String verify(CaptchaDTO captchaDTO);
}
