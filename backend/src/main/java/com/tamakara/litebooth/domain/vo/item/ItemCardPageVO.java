package com.tamakara.litebooth.domain.vo.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCardPageVO {
    private List<ItemCardVO> itemCardList;
    private Long pageNum;
    private Long pageSize;
    private Long total;
}
