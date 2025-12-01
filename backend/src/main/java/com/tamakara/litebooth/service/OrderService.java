package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.entity.Stock;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;

import java.util.List;

public interface OrderService extends IService<Order> {
    OrderInfoVO createOrder(OrderCreateFormDTO createFormDTO);

    OrderInfoVO payOrder(Long orderId);

    OrderInfoVO deliveryOrder(Long orderId);

    OrderInfoPageVO getOrderInfoPageVO(OrderInfoPageQueryFormDTO queryFormDTO);
}
