package com.tamakara.litebooth.domain.dto.order;

import com.tamakara.litebooth.domain.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateFormDTO {
    @NotNull(message = "商品ID不能为空")
    private Long itemId;
    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量至少为1")
    private Long quantity;
    @NotNull(message = "支付方式不能为空")
    private PaymentMethod paymentMethod;
    @NotBlank(message = "查询邮箱不能为空")
    private String queryEmail;
    private String queryPassword;
    @NotBlank(message = "验证码Token不能为空")
    private String captchaToken;
}
