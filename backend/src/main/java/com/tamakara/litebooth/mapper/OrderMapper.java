package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tamakara.litebooth.domain.dto.order.OrderInfoPageQueryFormDTO;
import com.tamakara.litebooth.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT DATE(created_at) as date, SUM(amount) as total FROM `order` WHERE status = 'FINISHED' AND created_at >= #{startDate} GROUP BY DATE(created_at)")
    List<Map<String, Object>> selectDailySales(@Param("startDate") String startDate);

    @Select("SELECT item_name as name, SUM(quantity) as total FROM `order` WHERE status = 'FINISHED' GROUP BY item_name ORDER BY total DESC LIMIT 5")
    List<Map<String, Object>> selectTopItems();

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
