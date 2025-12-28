package com.tamakara.litebooth.domain.vo.dashboard;

import lombok.Data;
import java.util.List;

@Data
public class DashboardVO {
    private CardDataVO totalSales;
    private CardDataVO totalOrders;
    private CardDataVO soldItems;
    private CardDataVO totalStock;

    private ChartDataVO salesTrend;

    private List<TopItemVO> topItems;

    private List<RecentOrderVO> recentOrders;
}

