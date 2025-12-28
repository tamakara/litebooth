package com.tamakara.litebooth.domain.dto.stock;

import lombok.Data;

@Data
public class StockUpdateDTO {
    private Long id;
    private Long orderId;
    private String content;
    private Boolean isSold;
}
