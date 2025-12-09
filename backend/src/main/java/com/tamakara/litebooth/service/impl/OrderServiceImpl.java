package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.order.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.*;
import com.tamakara.litebooth.domain.enums.OrderStatus;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.mapper.*;
import com.tamakara.litebooth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final StringRedisTemplate redisTemplate;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final StockMapper stockMapper;

    @Override
    @Transactional
    public OrderInfoVO createOrder(OrderCreateFormDTO createFormDTO) {
        Item item = itemMapper.selectById(createFormDTO.getItemId());
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }

        String token = "captcha:token:" + createFormDTO.getCaptchaToken();
        if (!redisTemplate.hasKey(token)) {
            throw new RuntimeException("验证码未校验或已失效");
        }

        String key = "captcha:key:" +redisTemplate.opsForValue().get("captcha:token:" + createFormDTO.getCaptchaToken());
        if (!redisTemplate.hasKey(key)) {
            throw new RuntimeException("验证码不存在");
        }

        redisTemplate.delete(key);
        redisTemplate.delete(token);


        Order order = new Order();
        order.setStatus(OrderStatus.UNPAID);
        order.setQueryEmail(createFormDTO.getQueryEmail());
        order.setQueryPassword(createFormDTO.getQueryPassword());
        order.setItemId(item.getId());
        order.setItemName(item.getName());
        order.setItemPrice(item.getPrice());
        order.setQuantity(createFormDTO.getQuantity());
        order.setAmount(item.getPrice() * createFormDTO.getQuantity());
        order.setPaymentMethod(createFormDTO.getPaymentMethod());

        orderMapper.insert(order);

        Long quantity = order.getQuantity();
        while (quantity-- > 0) {
            Stock stock = stockMapper.selectByItemId(order.getItemId());
            stock.setOrderId(order.getId());
            stock.setIsSold(true);
            stockMapper.updateById(stock);
        }

        order = orderMapper.selectById(order.getId());
        OrderInfoVO vo = new OrderInfoVO(order, null);

        return vo;
    }

    @Override
    @Transactional
    public OrderInfoVO payOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        order.setStatus(OrderStatus.FINISHED);
        order.setPaymentAt(Instant.now());
        orderMapper.updateById(order);

        List<Stock> stockList = stockMapper.selectListByOrderId(orderId);
        List<String> contentList = stockList
                .stream()
                .map(Stock::getContent)
                .toList();

        OrderInfoVO vo = new OrderInfoVO(order, contentList);

        return vo;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderInfoPageVO getOrderInfoPageVO(OrderInfoPageQueryFormDTO queryFormDTO) {
        Page<Order> page = orderMapper.selectPageByQueryForm(queryFormDTO);

        List<OrderInfoVO> orderVOList = page
                .getRecords()
                .stream()
                .map(order -> {
                    List<Stock> stockList = stockMapper.selectListByOrderId(order.getId());
                    List<String> contentList = stockList.stream().map(Stock::getContent).toList();
                    return new OrderInfoVO(order, contentList);
                }).toList();

        OrderInfoPageVO vo = new OrderInfoPageVO(orderVOList, page.getCurrent(), page.getSize(), page.getTotal());
        return vo;
    }
}
