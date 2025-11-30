package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.dto.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;

public interface OrderService extends IService<Order> {
    OrderInfoVO createOrder( OrderCreateFormDTO orderFormDTO);

    void payOrder( Long orderId);

    void deliveryOrder( Long orderId);

//    OrderInfoPageVO getOrderInfoPageVO(Long pageNum, Long pageSize);
}
