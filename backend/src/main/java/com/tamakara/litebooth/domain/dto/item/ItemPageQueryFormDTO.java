package com.tamakara.litebooth.domain.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPageQueryFormDTO {
    private String keyword;
    private Long groupId;
    private Long pageNum = 1L;
    private Long pageSize = 10L;
}
