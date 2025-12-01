package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
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
            @RequestBody OrderCreateFormDTO orderFormDTO
    ) {
        OrderInfoVO vo = orderService.createOrder(orderFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/payOrder/{orderId}")
    public ResponseEntity<OrderInfoVO> payOrder(
            @PathVariable("orderId") Long orderId
    ) {
        OrderInfoVO vo =  orderService.payOrder(orderId);
        return ResponseEntity.ok(vo);
    }

//    @GetMapping("/vo/OrderInfoPageVO")
//    public ResponseEntity<OrderInfoPageVO> getOrderInfoPageVO(
//            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
//            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize
//    ) {
//        OrderInfoPageVO vo = orderService.getOrderInfoPageVO(pageNum, pageSize);
//        return ResponseEntity.ok(vo);
//    }

}
