package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.item.ItemCreateOrUpdateFormDTO;
import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.item.ItemPageVO;
import com.tamakara.litebooth.domain.vo.item.ItemVO;
import com.tamakara.litebooth.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端商品模块接口")
@RestController
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class AdminItemController {
    private final ItemService itemService;

    @GetMapping("/getItemPageVO")
    public ResponseEntity<ItemPageVO> getItemPageVO(
            @ModelAttribute ItemPageQueryFormDTO itemPageQueryFormDTO
    ) {
        ItemPageVO vo = itemService.getItemPageVO(itemPageQueryFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/createItem")
    public ResponseEntity<ItemVO> createItem(
            @RequestBody ItemCreateOrUpdateFormDTO itemCreateOrUpdateFormDTO
    ) {
        ItemVO vo = itemService.createItem(itemCreateOrUpdateFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PutMapping("/updateItem")
    public ResponseEntity<ItemVO> updateItem(
            @RequestBody ItemCreateOrUpdateFormDTO itemCreateOrUpdateFormDTO
    ) {
        ItemVO vo = itemService.updateItem(itemCreateOrUpdateFormDTO);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable("id") Long id
    ) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}
