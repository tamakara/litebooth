package com.tamakara.litebooth.domain.dto.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaDTO {
    private String captchaKey;
    private String captchaCode;
}
