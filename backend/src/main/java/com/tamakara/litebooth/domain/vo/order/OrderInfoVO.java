package com.tamakara.litebooth.domain.vo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoVO {
    private String id;
    private String status;
    private String userMail;
    private String itemName;
    private Double itemPrice;
    private Long quantity;
    private String payMethod;
    private Double amount;
    private List<String> contentList;
    private Instant createdAt;
    private Instant updatedAt;
}
