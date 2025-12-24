package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.item.ItemPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.item.ItemPageVO;
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

        return ResponseEntity.ok().build();
    }

//    @PostMapping("/createGroup")
//    public ResponseEntity<GroupVO> createItem(
//
//    ) {
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/updateItem")
//    public ResponseEntity<GroupVO> updateItem(
//
//    ) {
//
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/deleteItem/{id}")
//    public ResponseEntity<Void> deleteItem(
//            @PathVariable("id") String id
//    ) {
//        return ResponseEntity.ok().build();
//    }
}
