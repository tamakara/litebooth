package com.tamakara.litebooth.domain.dto;

import lombok.Data;

@Data
public class CaptchaDTO {
    private String captchaKey;
    private String captchaCode;
}
