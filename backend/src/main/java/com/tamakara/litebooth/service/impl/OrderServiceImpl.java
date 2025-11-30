package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.OrderCreateFormDTO;
import com.tamakara.litebooth.domain.entity.*;
import com.tamakara.litebooth.domain.vo.order.OrderInfoVO;
import com.tamakara.litebooth.domain.vo.order.OrderInfoPageVO;
import com.tamakara.litebooth.mapper.*;
import com.tamakara.litebooth.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final StockMapper stockMapper;

    @Override
    @Transactional
    public OrderInfoVO createOrder(OrderCreateFormDTO orderFormDTO) {
        Item item = itemMapper.selectById(orderFormDTO.getItemId());
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }

        Order order = new Order();
        order.setStatus("未支付");
        order.setQueryEmail(orderFormDTO.getQueryEmail());
        order.setQueryPassword(orderFormDTO.getQueryPassword());
        order.setItemId(item.getId());
        order.setItemName(item.getName());
        order.setItemPrice(item.getPrice());
        order.setQuantity(orderFormDTO.getQuantity());
        order.setAmount(item.getPrice() * orderFormDTO.getQuantity());
        order.setPayMethod(orderFormDTO.getPayMethod());

        orderMapper.insert(order);

        order = orderMapper.selectById(order.getId());

        OrderInfoVO vo = new OrderInfoVO();
        vo.setId(String.valueOf(order.getId()));
        vo.setQueryEmail(order.getQueryEmail());
        vo.setQueryPassword(order.getQueryPassword());
        vo.setStatus(order.getStatus());
        vo.setItemName(order.getItemName());
        vo.setItemPrice(order.getItemPrice());
        vo.setQuantity(order.getQuantity());
        vo.setAmount(order.getAmount());
        vo.setPayMethod(order.getPayMethod());
        vo.setCreatedAt(order.getCreatedAt());

        return vo;
    }


    @Override
    @Transactional
    public void payOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        order.setStatus("待发货");
        orderMapper.updateById(order);

        Item item = itemMapper.selectById(order.getItemId());

        if (item.getIsAutoDelivery()) {
            deliveryOrder(orderId);
        }
    }

    @Override
    @Transactional
    public void deliveryOrder(Long orderId) {
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


//    @Override
//    @Transactional(readOnly = true)
//    public OrderInfoPageVO getOrderInfoPageVO(Long pageNum, Long pageSize) {
//        Page<Order> page = new Page<>(pageNum, pageSize);
//        orderMapper.selectPageByUserId(page, userId);
//        List<OrderInfoVO> orderVOList = page.getRecords().stream().map(order -> {
//            OrderInfoVO vo = new OrderInfoVO();
//
//            List<Stock> stockList = stockMapper.selectListByOrderId(order.getId());
//            List<String> contentList = stockList.stream().map(Stock::getContent).toList();
//
//            vo.setId(order.getId().toString());
//            vo.setStatus(order.getStatus());
//            vo.setUserMail(user.getEmail());
//            vo.setItemName(order.getItemName());
//            vo.setItemPrice(order.getItemPrice());
//            vo.setQuantity(order.getQuantity());
//            vo.setPayMethod(order.getPayMethod());
//            vo.setAmount(order.getAmount());
//            vo.setContentList(contentList);
//            vo.setCreatedAt(order.getCreatedAt());
//            vo.setUpdatedAt(order.getUpdatedAt());
//            return vo;
//        }).toList();
//
//        return new OrderInfoPageVO(orderVOList, page.getCurrent(), page.getSize(), page.getTotal());
//    }
}
