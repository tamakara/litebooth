package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardListPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;

public interface ItemService extends IService<Item> {
    ItemCardListPageVO getItemCardListVO(String keyword, String group, Long pageNumber, Long pageSize);

    ItemInfoVO getItemVO(Long itemId);
}
