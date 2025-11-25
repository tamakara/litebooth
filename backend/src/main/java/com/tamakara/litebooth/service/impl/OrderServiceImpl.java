package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.OrderFormDTO;
import com.tamakara.litebooth.domain.entity.*;
import com.tamakara.litebooth.domain.vo.order.OrderVO;
import com.tamakara.litebooth.mapper.*;
import com.tamakara.litebooth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;
    private final StockMapper stockMapper;
    @Override
    @Transactional
    public OrderVO createOrder(Long userId, OrderFormDTO orderFormDTO) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        Item item = itemMapper.selectById(orderFormDTO.getItemId());
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setUserMail(user.getEmail());
        order.setStatus("未支付");
        order.setItemId(item.getId());
        order.setItemName(item.getName());
        order.setItemPrice(item.getPrice());
        order.setQuantity(orderFormDTO.getQuantity());
        order.setTotalPrice(item.getPrice() * orderFormDTO.getQuantity());
        order.setPayMethod(orderFormDTO.getPayMethod());

        orderMapper.insert(order);

        order = orderMapper.selectById(order.getId());

        OrderVO vo = new OrderVO();
        vo.setId(String.valueOf(order.getId()));
        vo.setUserMail(order.getUserMail());
        vo.setStatus(order.getStatus());
        vo.setItemName(order.getItemName());
        vo.setItemPrice(order.getItemPrice());
        vo.setQuantity(order.getQuantity());
        vo.setTotalPrice(order.getTotalPrice());
        vo.setPayMethod(order.getPayMethod());
        vo.setCreatedAt(order.getCreatedAt());

        return vo;
    }

    @Override
    @Transactional
    public void cancelOrder(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new RuntimeException("订单不存在或无权限取消");
        }

        if (!"未支付".equals(order.getStatus())) {
            throw new RuntimeException("只有未支付的订单才能取消");
        }

        orderMapper.deleteById(orderId);
    }

    @Override
    @Transactional
    public void payOrder(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        order.setStatus("待发货");
        orderMapper.updateById(order);

        Item item = itemMapper.selectById(order.getItemId());

        if (item.getIsAutoDelivery()) {
            deliveryOrder(0L, orderId);
        }
    }

    @Override
    @Transactional
    public void deliveryOrder(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);

        Long quantity = order.getQuantity();
        while (quantity-- > 0) {
            Stock stock = stockMapper.selectByItemId(order.getItemId());
            stock.setOrderId(orderId);
            stock.setStatus("已发货");
            stockMapper.updateById(stock);
        }

        order.setStatus("已发货");
        orderMapper.updateById(order);
    }
}
