package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.vo.dashboard.DashboardVO;
import com.tamakara.litebooth.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理端首页模块接口")
@RestController
@RequestMapping("/admin/home")
@RequiredArgsConstructor
public class AdminHomeController {
    private final DashboardService dashboardService;

    @GetMapping("/getDashboardData")
    public ResponseEntity<DashboardVO> getDashboardData() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }
}
