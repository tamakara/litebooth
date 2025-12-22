package com.tamakara.litebooth.domain.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPageQueryFormDTO {
    private String keyword;
    private Long pageNum;
    private Long pageSize;
}
