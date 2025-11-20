package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.ItemCardListVO;
import com.tamakara.litebooth.domain.vo.ItemInfoVO;

public interface ItemService extends IService<Item> {
    ItemCardListVO getItemCardListVO(String keyword, String group, Long pageNumber, Long pageSize);

    ItemInfoVO getItemInfoVO(Long itemId);
}
