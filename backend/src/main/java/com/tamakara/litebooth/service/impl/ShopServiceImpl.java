package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.shop.ShopInfoUpdateFormDTO;
import com.tamakara.litebooth.domain.entity.File;
import com.tamakara.litebooth.domain.entity.ShopInfo;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoVO;
import com.tamakara.litebooth.mapper.ShopInfoMapper;
import com.tamakara.litebooth.service.FileService;
import com.tamakara.litebooth.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements ShopService {
    private final ShopInfoMapper shopInfoMapper;
    private final FileService fileService;

    @Value("${minio.url-expires}")
    private Integer MINIO_URL_EXPIRES;

    @Override
    @Transactional(readOnly = true)
    public ShopInfoVO getShopInfoVO() {
        ShopInfo homeInfo = shopInfoMapper.selectById(1L);

        String url = fileService.getFileUrl(homeInfo.getLogoFileId(), MINIO_URL_EXPIRES);

        ShopInfoVO vo = new ShopInfoVO();
        vo.setLogoFileId(homeInfo.getLogoFileId());
        vo.setLogoFileUrl(url);
        vo.setLogoTitle(homeInfo.getLogoTitle());
        vo.setHomeTitle(homeInfo.getHomeTitle());
        vo.setHomeSubtitle(homeInfo.getHomeSubtitle());
        vo.setHomeAnnouncement(homeInfo.getHomeAnnouncement());

        return vo;
    }

    @Override
    @Transactional
    public void updateShopInfo(ShopInfoUpdateFormDTO shopInfoUpdateFormDTO) {
        ShopInfo homeInfo = shopInfoMapper.selectById(1L);

        Long logoFileId = shopInfoUpdateFormDTO.getLogoFileId();
        boolean exists = fileService.exists(new LambdaQueryWrapper<File>().eq(File::getId, logoFileId));
        if (!exists) {
            throw new RuntimeException("文件不存在");
        }

        homeInfo.setLogoFileId(logoFileId);
        homeInfo.setLogoTitle(shopInfoUpdateFormDTO.getLogoTitle());
        homeInfo.setHomeTitle(shopInfoUpdateFormDTO.getHomeTitle());
        homeInfo.setHomeSubtitle(shopInfoUpdateFormDTO.getHomeSubtitle());
        homeInfo.setHomeAnnouncement(shopInfoUpdateFormDTO.getHomeAnnouncement());

        shopInfoMapper.updateById(homeInfo);
    }
}
