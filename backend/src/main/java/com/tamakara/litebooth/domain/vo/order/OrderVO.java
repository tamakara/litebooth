package com.tamakara.litebooth.domain.vo.order;

import com.tamakara.litebooth.domain.enums.OrderStatus;
import com.tamakara.litebooth.domain.enums.PaymentMethod;
import lombok.Data;

import java.time.Instant;

@Data
public class OrderVO {
    private Long id;
    private OrderStatus status;
    private String queryEmail;
    private Long itemId;
    private String itemName;
    private Double itemPrice;
    private Long quantity;
    private Double amount;
    private PaymentMethod paymentMethod;
    private Instant createdAt;
    private Instant paymentAt;
}

