import { http } from "@/utils/http";

export type Group = {
  id?: number;
  name: string;
};

export type GroupResult = {
  success: boolean;
  data: Group[];
};

export const getGroupList = (params?: object) => {
  return http.request<GroupResult>("get", "/group/list", { params });
};

export const createGroup = (data: object) => {
  return http.request<GroupResult>("post", "/group/create", { data });
};

export const updateGroup = (data: object) => {
  return http.request<GroupResult>("put", "/group/update", { data });
};

export const deleteGroup = (id: number) => {
  return http.request<GroupResult>("delete", `/group/delete/${id}`);
};
