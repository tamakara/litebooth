package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.item.ItemCardPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemInfoVO;
import com.tamakara.litebooth.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户端商品模块接口")
@RestController
@RequestMapping("/user/items")
@RequiredArgsConstructor
public class UserItemController {
    private final ItemService itemService;

    @GetMapping
    public Result<ItemCardPageVO> listItems(
            @ModelAttribute ItemPageQueryFormDTO itemPageQueryFormDTO
    ) {
        ItemCardPageVO vo = itemService.getItemCardPageVO(itemPageQueryFormDTO);
        return Result.success(vo);
    }

    @GetMapping("/{itemId}")
    public Result<ItemInfoVO> getItem(
            @PathVariable("itemId") Long itemId
    ) {
        ItemInfoVO vo = itemService.getItemInfoVO(itemId);
        return Result.success(vo);
    }
}
