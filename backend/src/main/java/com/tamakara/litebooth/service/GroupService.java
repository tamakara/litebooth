package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.group.GroupPageQueryFormDTO;
import com.tamakara.litebooth.domain.dto.group.GroupUpdateFormDTO;
import com.tamakara.litebooth.domain.entity.Group;
import com.tamakara.litebooth.domain.vo.group.GroupPageVO;
import com.tamakara.litebooth.domain.vo.group.GroupVO;

import java.util.List;

public interface GroupService extends IService<Group> {
    List<GroupVO> getGroupListVO();

    GroupPageVO getOrderInfoPageVO(GroupPageQueryFormDTO groupPageQueryFormDTO);

    GroupVO createGroup(String name);

    GroupVO updateGroup(GroupUpdateFormDTO groupUpdateFormDTO);

    void deleteGroup(String id);
}
