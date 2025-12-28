import { http } from "@/utils/http";

export interface CardDataVO {
  name: string;
  value: string;
  percent: string;
  data: number[];
}

export interface ChartDataVO {
  dates: string[];
  thisWeekData: number[];
  lastWeekData: number[];
}

export interface TopItemVO {
  name: string;
  percentage: number;
  valueStr: string;
}

export interface RecentOrderVO {
  id: number;
  itemName: string;
  amount: number;
  status: string;
  createdAt: string;
}

export interface DashboardVO {
  totalSales: CardDataVO;
  totalOrders: CardDataVO;
  soldItems: CardDataVO;
  totalStock: CardDataVO;
  salesTrend: ChartDataVO;
  topItems: TopItemVO[];
  recentOrders: RecentOrderVO[];
}

export const getDashboardData = () => {
  return http.request<DashboardVO>("get", "/home/getDashboardData");
};

