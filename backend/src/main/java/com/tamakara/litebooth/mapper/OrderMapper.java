package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tamakara.litebooth.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
