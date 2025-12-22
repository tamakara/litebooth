package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.domain.vo.group.GroupVO;
import com.tamakara.litebooth.service.GroupService;
import com.tamakara.litebooth.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户端商品组模块接口")
@RestController
@RequestMapping("/user/group")
@RequiredArgsConstructor
public class UserGroupController {
    private final GroupService groupService;

    @GetMapping("/getGroupListVO")
    public ResponseEntity<List<GroupVO>> getGroupListVO() {
        List<GroupVO> vo = groupService.getGroupListVO();
        return ResponseEntity.ok(vo);
    }
}
