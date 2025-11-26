package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;

public interface OrderService extends IService<Order> {
    OrderInfoVO createOrder(Long userId, OrderCreateFormDTO orderFormDTO);

    void cancelOrder(Long userId, Long orderId);

    void payOrder(Long userId, Long orderId);

    void deliveryOrder(Long userId, Long orderId);

    OrderInfoPageVO getOrderInfoPageVO(Long userId , Long pageNum, Long pageSize);
}
