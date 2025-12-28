package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.order.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.dto.order.OrderPageQueryDTO;
import com.tamakara.litebooth.domain.entity.Item;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.enums.OrderStatus;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderPageVO;
import com.tamakara.litebooth.domain.vo.order.OrderVO;
import com.tamakara.litebooth.mapper.OrderMapper;
import com.tamakara.litebooth.service.ItemService;
import com.tamakara.litebooth.service.OrderService;
import com.tamakara.litebooth.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final ItemService itemService;
    private final StockService stockService;

    @Override
    public OrderPageVO getOrderPageVO(OrderPageQueryDTO dto) {
        Page<Order> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(dto.getKeyword())) {
            wrapper.and(w -> w.like(Order::getQueryEmail, dto.getKeyword())
                    .or()
                    .like(Order::getItemName, dto.getKeyword()));
        }

        if (dto.getStatus() != null) {
            wrapper.eq(Order::getStatus, dto.getStatus());
        }

        wrapper.orderByDesc(Order::getCreatedAt);

        page(page, wrapper);

        OrderPageVO vo = new OrderPageVO();
        vo.setTotal(page.getTotal());
        vo.setSize(page.getSize());
        vo.setCurrent(page.getCurrent());

        List<OrderVO> records = page.getRecords().stream().map(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            return orderVO;
        }).collect(Collectors.toList());

        vo.setRecords(records);

        return vo;
    }

    @Override
    @Transactional
    public OrderInfoVO createOrder(OrderCreateFormDTO createFormDTO) {
        Item item = itemService.getById(createFormDTO.getItemId());
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }

        Order order = new Order();
        order.setItemId(item.getId());
        order.setItemName(item.getName());
        order.setItemPrice(item.getPrice());
        order.setQuantity(createFormDTO.getQuantity());
        order.setAmount(item.getPrice() * createFormDTO.getQuantity());
        order.setPaymentMethod(createFormDTO.getPaymentMethod());
        order.setQueryEmail(createFormDTO.getQueryEmail());
        order.setQueryPassword(createFormDTO.getQueryPassword());
        order.setStatus(OrderStatus.UNPAID);
        order.setCreatedAt(Instant.now());

        save(order);

        return new OrderInfoVO(order, Collections.emptyList());
    }

    @Override
    @Transactional
    public OrderInfoVO payOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (order.getStatus() == OrderStatus.FINISHED) {
            return new OrderInfoVO(order, stockService.getStockContentByOrderId(orderId));
        }

        // Allocate stock
        List<String> contents = stockService.allocateStock(order.getItemId(), order.getId(), order.getQuantity());

        order.setStatus(OrderStatus.FINISHED);
        order.setPaymentAt(Instant.now());
        updateById(order);

        return new OrderInfoVO(order, contents);
    }

    @Override
    public OrderInfoPageVO getOrderInfoPageVO(OrderInfoPageQueryFormDTO queryFormDTO) {
        Page<Order> page = new Page<>(queryFormDTO.getPageNum(), queryFormDTO.getPageSize());
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();

        if ("orderId".equals(queryFormDTO.getQueryMode())) {
             if (StringUtils.hasText(queryFormDTO.getOrderId())) {
                 wrapper.eq(Order::getId, Long.parseLong(queryFormDTO.getOrderId()));
             }
        } else {
            if (StringUtils.hasText(queryFormDTO.getQueryEmail()) && StringUtils.hasText(queryFormDTO.getQueryPassword())) {
                wrapper.eq(Order::getQueryEmail, queryFormDTO.getQueryEmail())
                       .eq(Order::getQueryPassword, queryFormDTO.getQueryPassword());
            }
        }

        wrapper.orderByDesc(Order::getCreatedAt);
        page(page, wrapper);

        List<OrderInfoVO> records = page.getRecords().stream().map(order -> {
            List<String> contentList = Collections.emptyList();
            if (order.getStatus() == OrderStatus.FINISHED) {
                contentList = stockService.getStockContentByOrderId(order.getId());
            }
            return new OrderInfoVO(order, contentList);
        }).collect(Collectors.toList());

        return new OrderInfoPageVO(records, page.getCurrent(), page.getSize(), page.getTotal());
    }
}
