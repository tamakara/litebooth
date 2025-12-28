package com.tamakara.litebooth.domain.vo.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartDataVO {
    private List<String> dates;
    private List<Double> thisWeekData;
    private List<Double> lastWeekData;
}

