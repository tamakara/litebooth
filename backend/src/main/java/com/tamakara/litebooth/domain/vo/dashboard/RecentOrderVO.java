package com.tamakara.litebooth.domain.vo.dashboard;

import com.tamakara.litebooth.domain.enums.OrderStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class RecentOrderVO {
    private Long id;
    private String itemName;
    private Double amount;
    private OrderStatus status;
    private Instant createdAt;
}

