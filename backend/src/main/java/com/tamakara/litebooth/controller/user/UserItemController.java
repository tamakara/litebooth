package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;
import com.tamakara.litebooth.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户端商品模块接口")
@RestController
@RequestMapping("/user/item")
@RequiredArgsConstructor
public class UserItemController {
    private final ItemService itemService;

    @GetMapping("/getItemCardPageVO")
    public ResponseEntity<ItemCardPageVO> getItemCardPageVO(
            @ModelAttribute ItemPageQueryFormDTO itemPageQueryFormDTO
    ) {
        ItemCardPageVO vo = itemService.getItemCardPageVO(itemPageQueryFormDTO);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/getItemInfoVO/{itemId}")
    public ResponseEntity<ItemInfoVO> getItemVO(
            @PathVariable("itemId") Long itemId
    ) {
        ItemInfoVO vo = itemService.getItemVO(itemId);
        return ResponseEntity.ok(vo);
    }
}
