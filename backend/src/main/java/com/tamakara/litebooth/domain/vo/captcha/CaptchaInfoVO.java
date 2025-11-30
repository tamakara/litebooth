package com.tamakara.litebooth.domain.vo.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaInfoVO {
    private String captchaKey;
    private String imageBase64;
}
