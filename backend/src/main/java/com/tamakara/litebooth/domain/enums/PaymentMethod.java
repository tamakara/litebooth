package com.tamakara.litebooth.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentMethod {
    WXPAY,      // 微信支付
    ALIPAY      // 支付宝支付
}
