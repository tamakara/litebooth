package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.HomeInfo;
import com.tamakara.litebooth.domain.vo.home.HomeInfoVO;

public interface HomeService extends IService<HomeInfo> {
    HomeInfoVO getHomeInfoVO();

}
