package com.tamakara.litebooth.controller.user;

import com.tamakara.litebooth.domain.dto.order.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
import com.tamakara.litebooth.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户端订单模块接口")
@RestController
@RequestMapping("/user/order")
@RequiredArgsConstructor
public class UserOrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderInfoVO> createOrder(
            @RequestBody OrderCreateFormDTO createFormDTO
    ) {
        OrderInfoVO vo = orderService.createOrder(createFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/payOrder/{orderId}")
    public ResponseEntity<OrderInfoVO> payOrder(
            @PathVariable("orderId") Long orderId
    ) {
        OrderInfoVO vo = orderService.payOrder(orderId);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/getOrderInfoPageVO")
    public ResponseEntity<OrderInfoPageVO> getOrderInfoPageVO(
            @ModelAttribute OrderInfoPageQueryFormDTO queryFormDTO
    ) {
        OrderInfoPageVO vo = orderService.getOrderInfoPageVO(queryFormDTO);
        return ResponseEntity.ok(vo);
    }

}
