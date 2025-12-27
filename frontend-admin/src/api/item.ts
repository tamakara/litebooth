import {http} from "@/utils/http";
import {ItemPageQueryFormDTO, ItemPageVO, ItemCreateOrUpdateFormDTO} from "@/views/item/utils/types";

export const getItemList = (queryForm: ItemPageQueryFormDTO) => {
  return http.request<ItemPageVO>("get", "/item/getItemPageVO", {params: queryForm});
};

export const createItem = (data: ItemCreateOrUpdateFormDTO) => {
  return http.request<void>("post", "/item/createItem", {data});
};

export const updateItem = (data: ItemCreateOrUpdateFormDTO) => {
  return http.request<void>("put", "/item/updateItem", {data});
};

export const deleteItem = (id: number) => {
  return http.request<void>("delete", `/item/deleteItem/${id}`);
};

