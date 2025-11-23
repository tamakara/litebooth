package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.OrderFormDTO;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.entity.User;
import com.tamakara.litebooth.domain.vo.order.OrderVO;
import com.tamakara.litebooth.mapper.ItemMapper;
import com.tamakara.litebooth.mapper.OrderMapper;
import com.tamakara.litebooth.mapper.UserMapper;
import com.tamakara.litebooth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    @Override
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
    public void payOrder(Long userId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        order.setStatus("已支付");
        orderMapper.updateById(order);
    }
}
