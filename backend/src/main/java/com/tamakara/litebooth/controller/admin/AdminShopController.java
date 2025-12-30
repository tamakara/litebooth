package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.shop.ShopInfoUpdateFormDTO;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoVO;
import com.tamakara.litebooth.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端店铺模块接口")
@RestController
@RequestMapping("/admin/shop")
@RequiredArgsConstructor
public class AdminShopController {
    private final ShopService shopService;

    @GetMapping
    public Result<ShopInfoVO> getShopInfo() {
        ShopInfoVO vo = shopService.getShopInfoVO();
        return Result.success(vo);
    }

    @PutMapping
    public Result<Void> updateShopInfo(
            @RequestBody ShopInfoUpdateFormDTO shopInfoUpdateFormDTO
    ) {
        shopService.updateShopInfo(shopInfoUpdateFormDTO);
        return Result.success();
    }

}
