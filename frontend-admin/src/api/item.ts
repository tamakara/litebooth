import {http} from "@/utils/http";
import {ItemPageQueryFormDTO, ItemPageVO, ItemFormProps} from "@/views/item/utils/types";

export const getItemList = (queryForm: ItemPageQueryFormDTO) => {
  return http.request<ItemPageVO>("get", "/item/getItemPageVO", {params: queryForm});
};

export const createItem = (data: ItemFormProps) => {
  return http.request<void>("post", "/item/create", {data});
};

export const updateItem = (data: ItemFormProps) => {
  return http.request<void>("put", "/item/update", {data});
};

export const deleteItem = (id: number) => {
  return http.request<void>("delete", `/item/delete/${id}`);
};

