package com.tamakara.litebooth.domain.dto.order;

import com.tamakara.litebooth.domain.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderPageQueryDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String keyword; // Search by email or item name
    private OrderStatus status;
}

