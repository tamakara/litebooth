package com.tamakara.litebooth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFormDTO {
    private Long itemId;
    private Long quantity;
    private String payMethod;
}
