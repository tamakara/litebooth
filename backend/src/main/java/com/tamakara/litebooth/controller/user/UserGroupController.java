package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.vo.group.GroupVO;
import com.tamakara.litebooth.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户端商品组模块接口")
@RestController
@RequestMapping("/user/groups")
@RequiredArgsConstructor
public class UserGroupController {
    private final GroupService groupService;

    @GetMapping
    public Result<List<GroupVO>> listGroups() {
        List<GroupVO> vo = groupService.getGroupListVO();
        return Result.success(vo);
    }
}
