package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.vo.ItemCardListVO;
import com.tamakara.litebooth.domain.vo.ItemInfoVO;
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

    @GetMapping("/getItemCardListVO")
    public ResponseEntity<ItemCardListVO> getItemCardListVO(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "group", defaultValue = "全部") String group,
            @RequestParam(value = "pageNumber", defaultValue = "1") Long pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") Long pageSize
    ) {
        ItemCardListVO vo = itemService.getItemCardListVO(keyword, group, pageNumber, pageSize);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/getItemInfoVO/{itemId}")
    public ResponseEntity<ItemInfoVO> getItemInfoVO(
            @PathVariable("itemId") Long itemId
    ) {
        ItemInfoVO vo = itemService.getItemInfoVO(itemId);
        return ResponseEntity.ok(vo);
    }
}
