package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.vo.item.ItemCardListVO;
import com.tamakara.litebooth.domain.vo.item.ItemVO;
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

    @GetMapping("/getItemVO/{itemId}")
    public ResponseEntity<ItemVO> getItemVO(
            @PathVariable("itemId") Long itemId
    ) {
        ItemVO vo = itemService.getItemVO(itemId);
        return ResponseEntity.ok(vo);
    }
}
