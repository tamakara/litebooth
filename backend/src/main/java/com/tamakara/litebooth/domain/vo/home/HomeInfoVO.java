package com.tamakara.litebooth.domain.vo.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeInfoVO {
    private String title;
    private String subtitle;
    private String announcement;
    private List<String> groups;
}
