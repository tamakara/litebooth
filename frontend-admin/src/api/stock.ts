import { http } from "@/utils/http";
import { Result } from "@/utils/http/types";

export interface StockVO {
  id: number;
  isSold: boolean;
  itemId: number;
  orderId: number;
  content: string;
  createdAt: string;
  updatedAt: string;
}

export interface StockCreateDTO {
  itemId: number;
  content: string;
}

export interface StockUpdateDTO {
  id: number;
  orderId?: number;
  content?: string;
  isSold?: boolean;
}

export const getStockList = (itemId: number) => {
  return http.request<Result<StockVO[]>>("get", "/admin/stocks", { params: { itemId } });
};

export const createStock = (data: StockCreateDTO) => {
  return http.request<Result<void>>("post", "/admin/stocks", { data });
};

export const batchCreateStock = (data: { itemId: number; file: File }) => {
  const formData = new FormData();
  formData.append("itemId", data.itemId.toString());
  formData.append("file", data.file);
  return http.request<Result<void>>("post", "/admin/stocks/batch", {
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};

export const updateStock = (data: StockUpdateDTO) => {
  return http.request<Result<void>>("put", "/admin/stocks", { data });
};

export const deleteStock = (id: number) => {
  return http.request<Result<void>>("delete", `/admin/stocks/${id}`);
};
