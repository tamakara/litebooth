package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.vo.home.HomeInfoVO;
import com.tamakara.litebooth.service.HomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "首页接口")
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/fetchHomeInfoVO")
    public ResponseEntity<HomeInfoVO> fetchHomeInfoVO() {
        HomeInfoVO vo = homeService.getHomeInfoVO();
        return ResponseEntity.ok(vo);
    }
}
