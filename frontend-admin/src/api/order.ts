import { http } from "@/utils/http";

export interface OrderVO {
  id: number;
  status: string;
  queryEmail: string;
  itemId: number;
  itemName: string;
  itemPrice: number;
  quantity: number;
  amount: number;
  paymentMethod: string;
  createdAt: string;
  paymentAt: string;
}

export interface OrderPageVO {
  records: OrderVO[];
  total: number;
  size: number;
  current: number;
}

export interface OrderPageQueryDTO {
  pageNum: number;
  pageSize: number;
  keyword?: string;
  status?: string;
}

export const getOrderPageVO = (data: OrderPageQueryDTO) => {
  return http.request<OrderPageVO>("post", "/order/getOrderPageVO", { data });
};

