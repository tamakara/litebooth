package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.domain.dto.order.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
import com.tamakara.litebooth.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户端订单模块接口")
@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
public class UserOrderController {
    private final OrderService orderService;

    @PostMapping
    public Result<OrderInfoVO> createOrder(
            @RequestBody @Valid OrderCreateFormDTO createFormDTO
    ) {
        OrderInfoVO vo = orderService.createOrder(createFormDTO);
        return Result.success(vo);
    }

    @PostMapping("/{orderId}/pay")
    public Result<OrderInfoVO> payOrder(
            @PathVariable("orderId") Long orderId
    ) {
        OrderInfoVO vo = orderService.payOrder(orderId);
        return Result.success(vo);
    }

    @GetMapping
    public Result<OrderInfoPageVO> listOrders(
            @ModelAttribute OrderInfoPageQueryFormDTO queryFormDTO
    ) {
        OrderInfoPageVO vo = orderService.getOrderInfoPageVO(queryFormDTO);
        return Result.success(vo);
    }

}
