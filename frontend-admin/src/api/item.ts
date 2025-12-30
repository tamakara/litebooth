import {http} from "@/utils/http";
import {Result} from "@/utils/http/types";

export interface ItemCreateOrUpdateFormDTO {
  id?: number;
  isActive: boolean;
  name: string;
  price: number;
  groupId: number;
  coverFileId: number;
  coverFileUrl?: string;
  description: string;
}

export interface ItemVO {
  id: string;
  isActive: boolean;
  name: string;
  price: number;
  groupId: number;
  groupName: string;
  coverFileId: number;
  coverFileUrl: string;
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface ItemPageQueryFormDTO {
  keyword: string;
  groupId: number;
  pageNum: number;
  pageSize: number;
  total?: number; // Added total as it might be useful or expected by pagination components
}

export interface ItemPageVO {
  records: ItemVO[];
  pageNum: number;
  pageSize: number;
  total: number;
}

export const getItemList = (queryForm: ItemPageQueryFormDTO) => {
  return http.request<Result<ItemPageVO>>("get", "/admin/items", {params: queryForm});
};

export const createItem = (data: ItemCreateOrUpdateFormDTO) => {
  return http.request<Result<void>>("post", "/admin/items", {data});
};

export const updateItem = (data: ItemCreateOrUpdateFormDTO) => {
  return http.request<Result<void>>("put", "/admin/items", {data});
};

export const deleteItem = (id: number) => {
  return http.request<Result<void>>("delete", `/admin/items/${id}`);
};

