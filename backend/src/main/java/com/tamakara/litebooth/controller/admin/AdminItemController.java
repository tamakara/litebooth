package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.vo.group.GroupPageVO;
import com.tamakara.litebooth.domain.vo.group.GroupVO;
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
    public ResponseEntity<GroupPageVO> getItemPageVO(

    ) {

        return ResponseEntity.ok(vo);
    }

    @PostMapping("/createGroup")
    public ResponseEntity<GroupVO> createItem(

    ) {

        return ResponseEntity.ok(vo);
    }

    @PutMapping("/updateItem")
    public ResponseEntity<GroupVO> updateItem(

    ) {

        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable("id") String id
    ) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}
