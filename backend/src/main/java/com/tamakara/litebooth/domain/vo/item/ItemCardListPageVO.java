package com.tamakara.litebooth.domain.vo.item;

import com.tamakara.litebooth.domain.pojo.ItemCardVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCardListPageVO {
    private List<ItemCardVO> itemCardList;
    private Long pageNumber;
    private Long pageSize;
    private Long total;
}
