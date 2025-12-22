package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.shop.ShopInfoUpdateFormDTO;
import com.tamakara.litebooth.domain.entity.ShopInfo;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoUpdateFormVO;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoVO;

public interface ShopService extends IService<ShopInfo> {
    ShopInfoVO getShopInfoVO();

    void updateShopInfo(ShopInfoUpdateFormDTO shopInfoUpdateFormDTO);
}