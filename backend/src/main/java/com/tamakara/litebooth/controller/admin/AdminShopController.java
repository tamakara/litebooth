package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.shop.ShopInfoUpdateFormDTO;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoUpdateFormVO;
import com.tamakara.litebooth.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端店铺模块接口")
@RestController
@RequestMapping("/admin/shop")
@RequiredArgsConstructor
public class AdminShopController {
    private final ShopService shopService;

    @GetMapping("/vo/ShopInfoUpdateFormVO")
    public ResponseEntity<ShopInfoUpdateFormVO> getShopInfoUpdateFormVO(

    ) {
        ShopInfoUpdateFormVO vo = shopService.getShopInfoUpdateFormVO();
        return ResponseEntity.ok(vo);
    }

    @PutMapping("/updateShopInfo")
    public ResponseEntity<Void> updateShopInfo(
            @RequestBody ShopInfoUpdateFormDTO shopInfoUpdateFormDTO
    ) {
        shopService.updateShopInfo(shopInfoUpdateFormDTO);
        return ResponseEntity.ok().build();
    }

}
