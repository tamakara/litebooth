package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tamakara.litebooth.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    default void selectPageByUserId(Page<Order> page,Long userId) {
       selectPage(page,new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId));
    }
}
