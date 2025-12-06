package com.tamakara.litebooth.domain.dto;

import com.tamakara.litebooth.domain.enums.PaymentMethod;
import lombok.Data;

@Data
public class OrderCreateFormDTO {
    private String itemId;
    private Long quantity;
    private PaymentMethod paymentMethod;
    private String queryEmail;
    private String queryPassword;
    private String captchaKey;
    private String captchaCode;
}
