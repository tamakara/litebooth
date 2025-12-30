package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.order.OrderPageQueryDTO;
import com.tamakara.litebooth.domain.vo.order.OrderPageVO;
import com.tamakara.litebooth.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端订单模块接口")
@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @PostMapping("/search")
    public Result<OrderPageVO> listOrders(
            @RequestBody OrderPageQueryDTO dto
    ) {
        return Result.success(orderService.getOrderPageVO(dto));
    }
}
