package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardListVO;
import com.tamakara.litebooth.domain.vo.item.ItemVO;

public interface ItemService extends IService<Item> {
    ItemCardListVO getItemCardListVO(String keyword, String group, Long pageNumber, Long pageSize);

    ItemVO getItemVO(Long itemId);
}
