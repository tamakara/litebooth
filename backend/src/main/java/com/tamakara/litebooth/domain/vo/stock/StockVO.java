package com.tamakara.litebooth.domain.vo.stock;

import lombok.Data;
import java.time.Instant;

@Data
public class StockVO {
    private Long id;
    private Boolean isSold;
    private Long itemId;
    private Long orderId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
}

