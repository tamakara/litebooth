package com.tamakara.litebooth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeInfoVO {
    private String announcement;
    private String title;
    private String subtitle;
    private List<String> groups;
}
