package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardVO;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;
import com.tamakara.litebooth.mapper.ItemMapper;
import com.tamakara.litebooth.mapper.StockMapper;
import com.tamakara.litebooth.service.FileService;
import com.tamakara.litebooth.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    private final ItemMapper itemMapper;
    private final FileService fileService;
    private final StockMapper stockMapper;

    @Override
    public ItemCardPageVO getItemCardListVO(String keyword, String group, Long pageNum, Long pageSize) {
        Page<Item> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Item> wrapper =
                new LambdaQueryWrapper<Item>()
                        .eq(Item::getIsActive, true)
                        .like(Item::getName, keyword)
                        .eq(!group.equals("全部"), Item::getGroup, group);
        itemMapper.selectPage(page, wrapper);

        List<ItemCardVO> items = page
                .getRecords()
                .stream()
                .map(item ->
                        new ItemCardVO(
                                item.getId(),
                                item.getName(),
                                item.getPrice(),
                                item.getGroup(),
                                fileService.getFileUrl(item.getCover(), 86400)
                        )
                ).toList();

        return new ItemCardPageVO(items, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public ItemInfoVO getItemVO(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        ItemInfoVO vo = new ItemInfoVO();
        vo.setId(item.getId().toString());
        vo.setName(item.getName());
        vo.setGroup(item.getGroup());
        vo.setCover(fileService.getFileUrl(item.getCover(), 86400));
        vo.setPrice(item.getPrice());
        vo.setStock(stockMapper.selectCountByItemId(item.getId(), "未发货"));
        vo.setDescription(item.getDescription());
        return vo;
    }
}
