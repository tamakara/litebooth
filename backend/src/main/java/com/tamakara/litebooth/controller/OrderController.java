package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.OrderQueryFormDTO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单接口")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderInfoVO> createOrder(
            @RequestAttribute("userId") Long userId,
            @RequestBody OrderQueryFormDTO orderFormDTO
    ) {
        OrderInfoVO vo = orderService.createOrder(userId, orderFormDTO);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @RequestAttribute("userId") Long userId,
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/payOrder/{orderId}")
    public ResponseEntity<Void> payOrder(
            @RequestAttribute("userId") Long userId,
            @PathVariable("orderId") Long orderId
    ) {
        orderService.payOrder(userId, orderId);
        return ResponseEntity.ok().build();
    }



}
