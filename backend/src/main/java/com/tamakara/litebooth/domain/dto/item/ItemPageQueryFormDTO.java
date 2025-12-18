package com.tamakara.litebooth.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPageQueryFormDTO {
    private String keyword;
    private String group;
    private Long pageNum;
    private Long pageSize;
}
