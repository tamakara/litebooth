package com.tamakara.litebooth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tamakara.litebooth.domain.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    default Stock selectByItemId(Long itemId) {
        return this.selectOne(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getItemId, itemId)
                .eq(Stock::getStatus, "未发货")
                .last("limit 1")
        );
    }

    default List<Stock> selectListByOrderId(Long orderId) {
        return this.selectList(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getOrderId, orderId)
        );
    }

    default Long selectCountByItemId(Long itemId, String status) {
        return this.selectCount(new LambdaQueryWrapper<Stock>()
                .eq(Stock::getItemId, itemId)
                .eq(status != null, Stock::getStatus, status)
        );
    }

}
