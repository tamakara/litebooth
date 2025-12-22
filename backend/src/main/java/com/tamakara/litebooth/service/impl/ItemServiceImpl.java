package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemCardVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;
import com.tamakara.litebooth.mapper.ItemMapper;
import com.tamakara.litebooth.mapper.StockMapper;
import com.tamakara.litebooth.service.FileService;
import com.tamakara.litebooth.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    private final ItemMapper itemMapper;
    private final FileService fileService;
    private final StockMapper stockMapper;

    @Value("${minio.url-expires}")
    private Integer MINIO_URL_EXPIRES;

    @Override
    public ItemCardPageVO getItemCardPageVO(ItemPageQueryFormDTO itemPageQueryFormDTO) {
        String keyword = itemPageQueryFormDTO.getKeyword();
        Long groupId = itemPageQueryFormDTO.getGroupId();
        Long pageNum = itemPageQueryFormDTO.getPageNum();
        Long pageSize = itemPageQueryFormDTO.getPageSize();

        Page<Item> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Item> wrapper =
                new LambdaQueryWrapper<Item>()
                        .eq(Item::getIsActive, true)
                        .like(Item::getName, keyword)
                        .eq(groupId != 0, Item::getGroupId, groupId);
        itemMapper.selectPage(page, wrapper);

        List<ItemCardVO> records = page
                .getRecords()
                .stream()
                .map(item ->
                        new ItemCardVO(
                                item.getId(),
                                item.getName(),
                                item.getPrice(),
                                fileService.getFileUrl(item.getCover(), MINIO_URL_EXPIRES)
                        )
                ).toList();

        return new ItemCardPageVO(records, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public ItemInfoVO getItemVO(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("找不到商品");
        }
        ItemInfoVO vo = new ItemInfoVO();
        vo.setId(item.getId().toString());
        vo.setName(item.getName());
        vo.setCover(fileService.getFileUrl(item.getCover(), MINIO_URL_EXPIRES));
        vo.setPrice(item.getPrice());
        vo.setStock(stockMapper.selectCountByItemId(item.getId(), false));
        vo.setDescription(item.getDescription());
        return vo;
    }
}
