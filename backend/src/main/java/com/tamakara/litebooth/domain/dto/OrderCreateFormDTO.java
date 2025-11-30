package com.tamakara.litebooth.domain.dto;

import lombok.Data;

@Data
public class OrderCreateFormDTO {
    private String itemId;
    private Long quantity;
    private String payMethod;
    private String queryEmail;
    private String queryPassword;
    private String captchaKey;
    private String captchaCode;
}
