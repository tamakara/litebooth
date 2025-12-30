package com.tamakara.litebooth.domain.dto.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockCreateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long itemId;
    @NotBlank(message = "库存内容不能为空")
    private String content;
}
