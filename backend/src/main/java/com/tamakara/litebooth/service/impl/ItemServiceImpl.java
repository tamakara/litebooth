package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.pojo.ItemCard;
import com.tamakara.litebooth.domain.vo.ItemCardListVO;
import com.tamakara.litebooth.mapper.ItemMapper;
import com.tamakara.litebooth.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    private final ItemMapper itemMapper;

    @Override
    public ItemCardListVO getItemCardListVO(String keyword, String group, Long pageNumber, Long pageSize) {
        Page<Item> page = new Page<>(pageNumber, pageSize);
        LambdaQueryWrapper<Item> wrapper =
                new LambdaQueryWrapper<Item>()
                        .eq(Item::getIsActive, true)
                        .like(Item::getName, keyword)
                        .eq(!group.equals("全部"), Item::getGroup, group);
        itemMapper.selectPage(page, wrapper);

        List<ItemCard> items = page
                .getRecords()
                .stream()
                .map(item ->
                        new ItemCard(
                                item.getId(),
                                item.getName(),
                                item.getPrice(),
                                item.getGroup()
                        )
                ).toList();

        return new ItemCardListVO(items, page.getCurrent(), page.getSize(), page.getTotal());
    }
}
