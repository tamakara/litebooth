package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;
import com.tamakara.litebooth.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品接口")
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/vo/ItemCardPageVO")
    public ResponseEntity<ItemCardPageVO> getItemCardListVO(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "group", defaultValue = "全部") String group,
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") Long pageSize
    ) {
        ItemCardPageVO vo = itemService.getItemCardListVO(keyword, group, pageNum, pageSize);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/vo/ItemInfoVO/{itemId}")
    public ResponseEntity<ItemInfoVO> getItemVO(
            @PathVariable("itemId") Long itemId
    ) {
        ItemInfoVO vo = itemService.getItemVO(itemId);
        return ResponseEntity.ok(vo);
    }
}
