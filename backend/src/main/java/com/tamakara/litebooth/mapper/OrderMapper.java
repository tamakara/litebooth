package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    default Page<Order> selectPageByQueryForm(OrderInfoPageQueryFormDTO form) {
        return selectPage(
                new Page<>(form.getPageNum(), form.getPageSize()),
                new LambdaQueryWrapper<Order>()
                        .eq("orderId".equals(form.getQueryMode()), Order::getId, Long.parseLong(form.getOrderId()))
                        .eq("queryEmail".equals(form.getQueryMode()), Order::getQueryEmail, form.getQueryEmail())
                        .eq("queryEmail".equals(form.getQueryMode()), Order::getQueryPassword, form.getQueryPassword())

        );
    }

}
