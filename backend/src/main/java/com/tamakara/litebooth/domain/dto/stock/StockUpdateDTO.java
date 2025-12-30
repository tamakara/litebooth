package com.tamakara.litebooth.domain.dto.stock;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockUpdateDTO {
    @NotNull(message = "库存ID不能为空")
    private Long id;
    private Long orderId;
    private String content;
    private Boolean isSold;
}
