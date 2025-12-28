import { http } from "@/utils/http";

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
  return http.request<StockVO[]>("get", "/stock/getStockListVO", { params: { itemId } });
};

export const createStock = (data: StockCreateDTO) => {
  return http.request<void>("post", "/stock/createStock", { data });
};

export const batchCreateStock = (data: { itemId: number; file: File }) => {
  const formData = new FormData();
  formData.append("itemId", data.itemId.toString());
  formData.append("file", data.file);
  return http.request<void>("post", "/stock/batchCreateStock", {
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};

export const updateStock = (data: StockUpdateDTO) => {
  return http.request<void>("put", "/stock/updateStock", { data });
};

export const deleteStock = (id: number) => {
  return http.request<void>("delete", `/stock/deleteStock/${id}`);
};
