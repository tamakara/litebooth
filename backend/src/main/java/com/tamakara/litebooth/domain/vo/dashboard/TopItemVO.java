package com.tamakara.litebooth.domain.vo.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopItemVO {
    private String name;
    private Integer percentage;
    private String valueStr;
}

