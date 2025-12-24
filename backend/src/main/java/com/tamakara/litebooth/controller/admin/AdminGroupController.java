package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.group.GroupPageQueryFormDTO;
import com.tamakara.litebooth.domain.dto.group.GroupUpdateFormDTO;
import com.tamakara.litebooth.domain.vo.group.GroupPageVO;
import com.tamakara.litebooth.domain.vo.group.GroupVO;
import com.tamakara.litebooth.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理端商品组模块接口")
@RestController
@RequestMapping("/admin/group")
@RequiredArgsConstructor
public class AdminGroupController {
    private final GroupService groupService;

    @GetMapping("/getGroupListVO")
    public ResponseEntity<List<GroupVO>> getGroupListVO() {
        List<GroupVO> vo = groupService.getGroupListVO();
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/getGroupPageVO")
    public ResponseEntity<GroupPageVO> getGroupPageVO(
            @ModelAttribute GroupPageQueryFormDTO groupPageQueryFormDTO
    ) {
        GroupPageVO vo = groupService.getGroupPageVO(groupPageQueryFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/createGroup/{name}")
    public ResponseEntity<GroupVO> createGroup(
            @PathVariable("name") String name
    ) {
        GroupVO vo = groupService.createGroup(name);
        return ResponseEntity.ok(vo);
    }

    @PutMapping("/updateGroup")
    public ResponseEntity<GroupVO> updateGroup(
            @RequestBody GroupUpdateFormDTO groupUpdateFormDTO
    ) {
        GroupVO vo = groupService.updateGroup(groupUpdateFormDTO);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("/deleteGroup/{id}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable("id") String id
    ) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}
