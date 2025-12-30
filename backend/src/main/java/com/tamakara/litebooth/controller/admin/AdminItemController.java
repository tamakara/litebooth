package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.item.ItemCreateOrUpdateFormDTO;
import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.item.ItemPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemVO;
import com.tamakara.litebooth.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端商品模块接口")
@RestController
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemController {
    private final ItemService itemService;

    @GetMapping
    public Result<ItemPageVO> listItems(
            @ModelAttribute ItemPageQueryFormDTO itemPageQueryFormDTO
    ) {
        ItemPageVO vo = itemService.getItemPageVO(itemPageQueryFormDTO);
        return Result.success(vo);
    }

    @PostMapping
    public Result<ItemVO> createItem(
            @RequestBody @Valid ItemCreateOrUpdateFormDTO itemCreateOrUpdateFormDTO
    ) {
        ItemVO vo = itemService.createItem(itemCreateOrUpdateFormDTO);
        return Result.success(vo);
    }

    @PutMapping
    public Result<ItemVO> updateItem(
            @RequestBody @Valid ItemCreateOrUpdateFormDTO itemCreateOrUpdateFormDTO
    ) {
        ItemVO vo = itemService.updateItem(itemCreateOrUpdateFormDTO);
        return Result.success(vo);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteItem(
            @PathVariable("id") Long id
    ) {
        itemService.deleteItem(id);
        return Result.success();
    }
}
