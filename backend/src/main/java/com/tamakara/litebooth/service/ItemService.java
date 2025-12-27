package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.item.ItemCreateOrUpdateFormDTO;
import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;
import com.tamakara.litebooth.domain.vo.item.ItemPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemVO;

public interface ItemService extends IService<Item> {
    ItemCardPageVO getItemCardPageVO(ItemPageQueryFormDTO itemPageQueryFormDTO);

    ItemInfoVO getItemInfoVO(Long itemId);

    ItemPageVO getItemPageVO(ItemPageQueryFormDTO itemPageQueryFormDTO);

    ItemVO createItem(ItemCreateOrUpdateFormDTO itemCreateFormDTO);

    ItemVO updateItem(ItemCreateOrUpdateFormDTO itemUpdateFormDTO);

    void deleteItem(Long id);
}
