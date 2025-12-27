package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.item.ItemCreateOrUpdateFormDTO;
import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.vo.item.*;
import com.tamakara.litebooth.mapper.ItemMapper;
import com.tamakara.litebooth.mapper.StockMapper;
import com.tamakara.litebooth.service.FileService;
import com.tamakara.litebooth.service.GroupService;
import com.tamakara.litebooth.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    private final ItemMapper itemMapper;
    private final FileService fileService;
    private final StockMapper stockMapper;
    private final GroupService groupService;

    @Value("${minio.url-expires}")
    private Integer MINIO_URL_EXPIRES;

    @Override
    @Transactional(readOnly = true)
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
                                fileService.getFileUrl(item.getCoverFileId(), MINIO_URL_EXPIRES)
                        )
                ).toList();

        return new ItemCardPageVO(records, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfoVO getItemInfoVO(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("找不到商品");
        }
        ItemInfoVO vo = new ItemInfoVO();
        vo.setId(item.getId().toString());
        vo.setName(item.getName());
        vo.setCoverFileUrl(fileService.getFileUrl(item.getCoverFileId(), MINIO_URL_EXPIRES));
        vo.setPrice(item.getPrice());
        vo.setStock(stockMapper.selectCountByItemId(item.getId(), false));
        vo.setDescription(item.getDescription());
        return vo;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemPageVO getItemPageVO(ItemPageQueryFormDTO itemPageQueryFormDTO) {
        String keyword = itemPageQueryFormDTO.getKeyword();
        Long groupId = itemPageQueryFormDTO.getGroupId();
        Long pageNum = itemPageQueryFormDTO.getPageNum();
        Long pageSize = itemPageQueryFormDTO.getPageSize();

        Page<Item> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Item> wrapper =
                new LambdaQueryWrapper<Item>()
                        .like(groupId != 0, Item::getGroupId, groupId)
                        .like(!"".equals(keyword), Item::getName, keyword);
        itemMapper.selectPage(page, wrapper);

        List<ItemVO> record = page
                .getRecords()
                .stream()
                .map(item -> {
                    ItemVO vo = new ItemVO();
                    vo.setId(item.getId());
                    vo.setIsActive(item.getIsActive());
                    vo.setName(item.getName());
                    vo.setPrice(item.getPrice());
                    vo.setGroupId(item.getGroupId());
                    vo.setGroupName(groupService.getGroupMapVO().get(item.getGroupId()));
                    vo.setCoverFileId(item.getCoverFileId());
                    vo.setCoverFileUrl(fileService.getFileUrl(item.getCoverFileId(), MINIO_URL_EXPIRES));
                    vo.setDescription(item.getDescription());
                    vo.setCreatedAt(item.getCreatedAt());
                    vo.setUpdatedAt(item.getUpdatedAt());
                    return vo;
                }).toList();

        return new ItemPageVO(record, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    @Transactional
    public ItemVO createItem(ItemCreateOrUpdateFormDTO itemCreateOrUpdateFormDTO) {
        Item item = new Item();
        item.setIsActive(itemCreateOrUpdateFormDTO.getIsActive());
        item.setName(itemCreateOrUpdateFormDTO.getName());
        item.setPrice(itemCreateOrUpdateFormDTO.getPrice());
        item.setGroupId(itemCreateOrUpdateFormDTO.getGroupId());
        item.setCoverFileId(itemCreateOrUpdateFormDTO.getCoverFileId());
        item.setDescription(itemCreateOrUpdateFormDTO.getDescription());

        itemMapper.insert(item);

        return null;
    }

    @Override
    @Transactional
    public ItemVO updateItem(ItemCreateOrUpdateFormDTO itemCreateOrUpdateFormDTO) {
        Item item = itemMapper.selectById(itemCreateOrUpdateFormDTO.getId());
        if (item == null) {
            throw new IllegalArgumentException("找不到商品");
        }

        item.setIsActive(itemCreateOrUpdateFormDTO.getIsActive());
        item.setName(itemCreateOrUpdateFormDTO.getName());
        item.setPrice(itemCreateOrUpdateFormDTO.getPrice());
        item.setGroupId(itemCreateOrUpdateFormDTO.getGroupId());
        item.setCoverFileId(itemCreateOrUpdateFormDTO.getCoverFileId());
        item.setDescription(itemCreateOrUpdateFormDTO.getDescription());
        itemMapper.updateById(item);

        ItemVO vo = new ItemVO();
        vo.setId(item.getId());
        vo.setIsActive(item.getIsActive());
        vo.setName(item.getName());
        vo.setPrice(item.getPrice());
        vo.setGroupId(item.getGroupId());
        vo.setGroupName(groupService.getGroupMapVO().get(item.getGroupId()));
        vo.setCoverFileId(item.getCoverFileId());
        vo.setCoverFileUrl(fileService.getFileUrl(item.getCoverFileId(), MINIO_URL_EXPIRES));
        vo.setDescription(item.getDescription());
        vo.setCreatedAt(item.getCreatedAt());
        vo.setUpdatedAt(item.getUpdatedAt());

        return vo;
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        itemMapper.deleteById(id);
    }
}
