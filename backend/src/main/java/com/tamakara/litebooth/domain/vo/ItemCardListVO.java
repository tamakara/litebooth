package com.tamakara.litebooth.domain.vo;

import com.tamakara.litebooth.domain.pojo.ItemCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCardListVO {
    private List<ItemCard> items;
    private Long pageNumber;
    private Long pageSize;
    private Long total;
}
