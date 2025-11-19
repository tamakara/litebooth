package com.tamakara.litebooth.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCard {
    private Long id;
    private String name;
    private Double price;
    private String group;
    private String cover;
}
