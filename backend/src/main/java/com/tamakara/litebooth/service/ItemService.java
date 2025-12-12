package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;

import java.util.List;

public interface ItemService extends IService<Item> {
    List<String> getGroupListVO();

    ItemCardPageVO getItemCardListVO(String keyword, String group, Long pageNum, Long pageSize);

    ItemInfoVO getItemVO(Long itemId);
}
