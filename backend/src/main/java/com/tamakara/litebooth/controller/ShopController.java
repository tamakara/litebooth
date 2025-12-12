package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.shop.ShopInfoUpdateFormDTO;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoVO;
import com.tamakara.litebooth.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "店铺接口")
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/vo/ShopInfoVO")
    public ResponseEntity<ShopInfoVO> getShopInfoVO() {
        ShopInfoVO vo = shopService.getShopInfoVO();
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
