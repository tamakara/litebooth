package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoVO;
import com.tamakara.litebooth.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户端店铺模块接口")
@RestController
@RequestMapping("/user/shop")
@RequiredArgsConstructor
public class UserShopController {
    private final ShopService shopService;

    @GetMapping
    public Result<ShopInfoVO> getShopInfo() {
        ShopInfoVO vo = shopService.getShopInfoVO();
        return Result.success(vo);
    }
}
