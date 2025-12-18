package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.auth.RefreshTokenDTO;
import com.tamakara.litebooth.domain.dto.shop.ShopInfoUpdateFormDTO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;
import com.tamakara.litebooth.domain.vo.auth.RefreshTokenVO;
import com.tamakara.litebooth.domain.vo.shop.ShopInfoUpdateFormVO;
import com.tamakara.litebooth.service.AuthService;
import com.tamakara.litebooth.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端接口")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthService authService;
    private final ShopService shopService;

    @PostMapping("/login")
    public ResponseEntity<LoginVO> login(
            @RequestBody LoginFormDTO loginFormDTO
    ) {
        LoginVO vo = authService.login(loginFormDTO);
        return ResponseEntity.ok(vo);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenVO> refreshToken(
            @RequestBody RefreshTokenDTO refreshTokenDTO
    ) {
        RefreshTokenVO vo = authService.refreshToken(refreshTokenDTO);
        return ResponseEntity.ok(vo);
    }


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
