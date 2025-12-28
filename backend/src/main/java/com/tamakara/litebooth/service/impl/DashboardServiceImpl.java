package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tamakara.litebooth.domain.entity.Order;
import com.tamakara.litebooth.domain.entity.Stock;
import com.tamakara.litebooth.domain.enums.OrderStatus;
import com.tamakara.litebooth.domain.vo.dashboard.*;
import com.tamakara.litebooth.mapper.OrderMapper;
import com.tamakara.litebooth.mapper.StockMapper;
import com.tamakara.litebooth.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final OrderMapper orderMapper;
    private final StockMapper stockMapper;

    @Override
    public DashboardVO getDashboardData() {
        DashboardVO vo = new DashboardVO();

        // 1. Card Data
        vo.setTotalSales(getTotalSalesCard());
        vo.setTotalOrders(getTotalOrdersCard());
        vo.setSoldItems(getSoldItemsCard());
        vo.setTotalStock(getTotalStockCard());

        // 2. Chart Data (Sales Trend)
        vo.setSalesTrend(getSalesTrendChart());

        // 3. Top Items (Mock for now or complex query)
        vo.setTopItems(getTopItems());

        // 4. Recent Orders
        vo.setRecentOrders(getRecentOrders());

        return vo;
    }

    private CardDataVO getTotalSalesCard() {
        // Total sales amount (FINISHED orders)
        List<Order> orders = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus, OrderStatus.FINISHED));
        double total = orders.stream().mapToDouble(Order::getAmount).sum();

        return new CardDataVO("总销售额", String.format("¥%.2f", total), "", Collections.emptyList());
    }

    private CardDataVO getTotalOrdersCard() {
        Long count = orderMapper.selectCount(null);
        return new CardDataVO("总订单数", count.toString(), "", Collections.emptyList());
    }

    private CardDataVO getSoldItemsCard() {
        List<Order> orders = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus, OrderStatus.FINISHED));
        long count = orders.stream().mapToLong(Order::getQuantity).sum();
        return new CardDataVO("已售商品数", String.valueOf(count), "", Collections.emptyList());
    }

    private CardDataVO getTotalStockCard() {
        Long count = stockMapper.selectCount(new LambdaQueryWrapper<Stock>().eq(Stock::getIsSold, false));
        return new CardDataVO("剩余库存", count.toString(), "", Collections.emptyList());
    }

    private ChartDataVO getSalesTrendChart() {
        LocalDate now = LocalDate.now();
        LocalDate twoWeeksAgo = now.minusDays(13);

        List<Map<String, Object>> dailySales = orderMapper.selectDailySales(twoWeeksAgo.toString());
        Map<String, Double> salesMap = dailySales.stream().collect(Collectors.toMap(
                m -> m.get("date").toString(),
                m -> Double.parseDouble(m.get("total").toString())
        ));

        List<String> dates = new ArrayList<>();
        List<Double> thisWeek = new ArrayList<>();
        List<Double> lastWeek = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = now.minusDays(i);
            LocalDate lastWeekDate = date.minusDays(7);

            dates.add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            thisWeek.add(salesMap.getOrDefault(date.toString(), 0.0));
            lastWeek.add(salesMap.getOrDefault(lastWeekDate.toString(), 0.0));
        }

        return new ChartDataVO(dates, thisWeek, lastWeek);
    }

    private List<TopItemVO> getTopItems() {
        List<Map<String, Object>> topItems = orderMapper.selectTopItems();
        if (topItems.isEmpty()) return Collections.emptyList();

        double maxTotal = Double.parseDouble(topItems.get(0).get("total").toString());

        return topItems.stream().map(m -> {
            String name = m.get("name").toString();
            double total = Double.parseDouble(m.get("total").toString());
            int percentage = (int) ((total / maxTotal) * 100);
            return new TopItemVO(name, percentage, String.valueOf((int)total));
        }).collect(Collectors.toList());
    }

    private List<RecentOrderVO> getRecentOrders() {
        List<Order> orders = orderMapper.selectList(new LambdaQueryWrapper<Order>()
                .orderByDesc(Order::getCreatedAt)
                .last("LIMIT 10"));

        return orders.stream().map(o -> {
            RecentOrderVO vo = new RecentOrderVO();
            vo.setId(o.getId());
            vo.setItemName(o.getItemName());
            vo.setAmount(o.getAmount());
            vo.setStatus(o.getStatus());
            vo.setCreatedAt(o.getCreatedAt());
            return vo;
        }).collect(Collectors.toList());
    }
}

