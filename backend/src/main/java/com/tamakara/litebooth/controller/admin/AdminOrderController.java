package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.domain.dto.order.OrderPageQueryDTO;
import com.tamakara.litebooth.domain.vo.order.OrderPageVO;
import com.tamakara.litebooth.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端订单模块接口")
@RestController
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @PostMapping("/getOrderPageVO")
    public ResponseEntity<OrderPageVO> getOrderPageVO(
            @RequestBody OrderPageQueryDTO dto
    ) {
        return ResponseEntity.ok(orderService.getOrderPageVO(dto));
    }
}

