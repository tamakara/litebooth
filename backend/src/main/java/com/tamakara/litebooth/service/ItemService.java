package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;

public interface ItemService extends IService<Item> {
    ItemCardPageVO getItemCardListVO(String keyword, String group, Long pageNum, Long pageSize);

    ItemInfoVO getItemVO(Long itemId);
}
