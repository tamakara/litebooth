package com.tamakara.litebooth.domain.dto.order;

import com.tamakara.litebooth.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateFormDTO {
    private Long itemId;
    private Long quantity;
    private PaymentMethod paymentMethod;
    private String queryEmail;
    private String queryPassword;
    private String captchaToken;
}
