package com.tamakara.litebooth.domain.vo.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoVO {
    private String title;
    private String subtitle;
    private String announcement;
    private List<String> groups;
}
