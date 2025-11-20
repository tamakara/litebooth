package com.tamakara.litebooth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoVO {
    private Long id;
    private String name;
    private String group;
    private String cover;
    private Double price;
    private Long stock;
    private String description;
}
