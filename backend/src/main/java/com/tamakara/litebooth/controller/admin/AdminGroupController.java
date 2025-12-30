package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.group.GroupPageQueryFormDTO;
import com.tamakara.litebooth.domain.dto.group.GroupUpdateFormDTO;
import com.tamakara.litebooth.domain.vo.group.GroupPageVO;
import com.tamakara.litebooth.domain.vo.group.GroupVO;
import com.tamakara.litebooth.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "管理端商品组模块接口")
@RestController
@RequestMapping("/admin/groups")
@RequiredArgsConstructor
public class AdminGroupController {
    private final GroupService groupService;

    @GetMapping("/list")
    public Result<List<GroupVO>> listGroups() {
        List<GroupVO> vo = groupService.getGroupListVO();
        return Result.success(vo);
    }

    @GetMapping("/map")
    public Result<Map<Long, String>> getGroupMap() {
        Map<Long, String> vo = groupService.getGroupMapVO();
        return Result.success(vo);
    }

    @GetMapping
    public Result<GroupPageVO> pageGroups(
            @ModelAttribute GroupPageQueryFormDTO groupPageQueryFormDTO
    ) {
        GroupPageVO vo = groupService.getGroupPageVO(groupPageQueryFormDTO);
        return Result.success(vo);
    }

    @PostMapping("/{name}")
    public Result<GroupVO> createGroup(
            @PathVariable("name") String name
    ) {
        GroupVO vo = groupService.createGroup(name);
        return Result.success(vo);
    }

    @PutMapping
    public Result<GroupVO> updateGroup(
            @RequestBody GroupUpdateFormDTO groupUpdateFormDTO
    ) {
        GroupVO vo = groupService.updateGroup(groupUpdateFormDTO);
        return Result.success(vo);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteGroup(
            @PathVariable("id") String id
    ) {
        groupService.deleteGroup(id);
        return Result.success();
    }
}
