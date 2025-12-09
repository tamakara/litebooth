package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.order.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;

public interface OrderService extends IService<Order> {
    OrderInfoVO createOrder(OrderCreateFormDTO createFormDTO);

    OrderInfoVO payOrder(Long orderId);

    OrderInfoPageVO getOrderInfoPageVO(OrderInfoPageQueryFormDTO queryFormDTO);
}
