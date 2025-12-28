package com.tamakara.litebooth.domain.vo.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDataVO {
    private String name;
    private String value;
    private String percent;
    private List<Double> data;
}

