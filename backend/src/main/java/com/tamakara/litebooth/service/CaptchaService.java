package com.tamakara.litebooth.service;

import com.tamakara.litebooth.domain.vo.captcha.CaptchaInfoVO;
public interface CaptchaService {
    CaptchaInfoVO getCaptchaInfoVO();

    Boolean verifyCaptcha(String captchaKey, String captchaCode);
}
