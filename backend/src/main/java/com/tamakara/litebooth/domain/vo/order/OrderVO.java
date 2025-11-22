package com.tamakara.litebooth.domain.vo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private String id;
    private String status;
    private String userMail;
    private String itemName;
    private Double itemPrice;
    private Long quantity;
    private String payMethod;
    private Double totalPrice;
    private Instant createdAt;
}
