package com.tamakara.litebooth.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderStatus {
    UNPAID,     // 待付款
    EXPIRED,     // 已过期
    FINISHED,    // 已完成
}
