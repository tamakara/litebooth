package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.OrderFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.vo.order.OrderVO;

public interface OrderService extends IService<Order> {
    OrderVO createOrder(Long userId, OrderFormDTO orderFormDTO);

    void cancelOrder(Long userId, Long orderId);

    void payOrder(Long userId, Long orderId);
}
