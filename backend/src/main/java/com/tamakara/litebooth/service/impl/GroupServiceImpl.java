package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.group.GroupPageQueryFormDTO;
import com.tamakara.litebooth.domain.dto.group.GroupUpdateFormDTO;
import com.tamakara.litebooth.domain.entity.Group;
import com.tamakara.litebooth.domain.vo.group.GroupPageVO;
import com.tamakara.litebooth.domain.vo.group.GroupVO;
import com.tamakara.litebooth.mapper.GroupMapper;
import com.tamakara.litebooth.service.AuthService;
import com.tamakara.litebooth.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {
    private final GroupMapper groupMapper;

    @Override
    public GroupPageVO getOrderInfoPageVO(GroupPageQueryFormDTO groupPageQueryFormDTO) {
        String name = groupPageQueryFormDTO.getName();
        Long pageNum = groupPageQueryFormDTO.getPageNum();
        Long pageSize = groupPageQueryFormDTO.getPageSize();

        Page<Group> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Group> wrapper =
                new LambdaQueryWrapper<Group>()
                        .eq(!"".equals(name), Group::getName, name);
        groupMapper.selectPage(page, wrapper);

        List<GroupVO> records = page
                .getRecords()
                .stream()
                .map(group ->
                        new GroupVO(
                                group.getId().toString(),
                                group.getName()
                        )
                ).toList();

        return new GroupPageVO(records, page.getCurrent(), page.getSize(), page.getTotal());

    }

    @Override
    public GroupVO createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        groupMapper.insert(group);

        GroupVO vo = new GroupVO();
        vo.setId(group.getId().toString());
        vo.setName(group.getName());

        return vo;
    }

    @Override
    public GroupVO updateGroup(GroupUpdateFormDTO groupUpdateFormDTO) {
        Group group = groupMapper.selectById(groupUpdateFormDTO.getId());
        group.setName(groupUpdateFormDTO.getName());
        groupMapper.updateById(group);

        GroupVO vo = new GroupVO();
        vo.setId(group.getId().toString());
        vo.setName(group.getName());

        return vo;
    }

    @Override
    public void deleteGroup(String id) {
        groupMapper.deleteById(Long.parseLong(id));
    }
}
