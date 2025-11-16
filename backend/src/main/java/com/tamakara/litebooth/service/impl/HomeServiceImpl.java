package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.entity.Group;
import com.tamakara.litebooth.domain.entity.HomeInfo;
import com.tamakara.litebooth.domain.vo.HomeInfoVO;
import com.tamakara.litebooth.mapper.GroupMapper;
import com.tamakara.litebooth.mapper.HomeMapper;
import com.tamakara.litebooth.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl extends ServiceImpl<HomeMapper, HomeInfo> implements HomeService {
    private final HomeMapper homeMapper;
    private final GroupMapper groupMapper;

    @Override
    public HomeInfoVO getHomeInfoVO() {
        HomeInfo homeInfo = homeMapper.selectById(0L);
        List<Group> groups = groupMapper.selectList(new LambdaQueryWrapper<>());

        HomeInfoVO vo = new HomeInfoVO();
        vo.setAnnouncement(homeInfo.getAnnouncement());
        vo.setTitle(homeInfo.getTitle());
        vo.setSubtitle(homeInfo.getSubtitle());
        vo.setGroups(groups.stream().map(Group::getName).toList());
        return vo;
    }
}
