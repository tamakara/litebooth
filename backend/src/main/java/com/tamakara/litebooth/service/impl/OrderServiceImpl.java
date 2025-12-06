package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.*;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.mapper.*;
import com.tamakara.litebooth.service.CaptchaService;
import com.tamakara.litebooth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final CaptchaService captchaService;
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

        Boolean ok = captchaService.verifyCaptcha(createFormDTO.getCaptchaKey(), createFormDTO.getCaptchaCode());
        if (!ok) {
            throw new RuntimeException("验证码错误");
        }

        Order order = new Order();
        order.setStatus("未支付");
        order.setQueryEmail(createFormDTO.getQueryEmail());
        order.setQueryPassword(createFormDTO.getQueryPassword());
        order.setItemId(item.getId());
        order.setItemName(item.getName());
        order.setItemPrice(item.getPrice());
        order.setQuantity(createFormDTO.getQuantity());
        order.setAmount(item.getPrice() * createFormDTO.getQuantity());
        order.setPaymentMethod(createFormDTO.getPayMethod());

        orderMapper.insert(order);

        order = orderMapper.selectById(order.getId());
        OrderInfoVO vo = new OrderInfoVO(order, null);

        return vo;
    }


    @Override
    @Transactional
    public OrderInfoVO payOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        order.setStatus("待发货");
        orderMapper.updateById(order);

        Item item = itemMapper.selectById(order.getItemId());

        OrderInfoVO vo;

        if (item.getIsAutoDelivery()) {
            vo = deliveryOrder(orderId);
        } else {
            vo = new OrderInfoVO(order, null);
        }

        return vo;
    }

    @Override
    @Transactional
    public OrderInfoVO deliveryOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);

        Long quantity = order.getQuantity();
        List<String> contentList = new ArrayList<>();
        while (quantity-- > 0) {
            Stock stock = stockMapper.selectByItemId(order.getItemId());
            stock.setOrderId(orderId);
            stock.setStatus("已发货");
            stockMapper.updateById(stock);
            contentList.add(stock.getContent());
        }

        order.setStatus("已发货");
        orderMapper.updateById(order);

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
