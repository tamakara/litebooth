package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.entity.Group;
import com.tamakara.litebooth.domain.entity.ShopInfo;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoVO;
import com.tamakara.litebooth.mapper.GroupMapper;
import com.tamakara.litebooth.mapper.ShopInfoMapper;
import com.tamakara.litebooth.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements ShopService {
    private final ShopInfoMapper shopInfoMapper;
    private final GroupMapper groupMapper;

    @Override
    public ShopInfoVO getShopInfoVO() {
        ShopInfo homeInfo = shopInfoMapper.selectById(1L);
        List<Group> groups = groupMapper.selectList(new LambdaQueryWrapper<>());

        ShopInfoVO vo = new ShopInfoVO();
        vo.setAnnouncement(homeInfo.getAnnouncement());
        vo.setTitle(homeInfo.getTitle());
        vo.setSubtitle(homeInfo.getSubtitle());
        vo.setGroups(groups.stream().map(Group::getName).toList());
        return vo;
    }
}
