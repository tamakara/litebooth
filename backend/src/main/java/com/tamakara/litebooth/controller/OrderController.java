package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.OrderFormDTO;
import com.tamakara.litebooth.domain.vo.order.OrderVO;
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
    public ResponseEntity<OrderVO> createOrder(
            @RequestAttribute("userId") Long userId,
            @RequestBody OrderFormDTO orderFormDTO
    ) {
        OrderVO vo = orderService.createOrder(userId,orderFormDTO);
        return ResponseEntity.ok(vo);
    }

}
