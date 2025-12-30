import {http} from "@/utils/http";
import {Result} from "@/utils/http/types";

export interface GroupVO {
  id: number;
  name: string;
}

export interface GroupUpdateFormDTO {
  id: number;
  name: string;
}

export interface GroupPageQueryFormDTO {
  keyword: string;
  pageNum: number;
  pageSize: number;
}

export type GroupListVO = GroupVO[];

export interface GroupPageVO {
  records: GroupListVO;
  pageNum: number;
  pageSize: number;
  total: number;
}

export type GroupMapVO = Record<number, string>;

export const getGroupListVO = () => {
  return http.request<Result<GroupListVO>>("get", "/admin/groups/list");
};

export const getGroupMapVO = () => {
  return http.request<Result<GroupMapVO>>("get", "/admin/groups/map");
};

export const getGroupPageVO = (queryForm: GroupPageQueryFormDTO) => {
  return http.request<Result<GroupPageVO>>("get", "/admin/groups", {params: queryForm});
};

export const createGroup = (name: string) => {
  return http.request<Result<GroupVO>>("post", `/admin/groups/${name}`);
};

export const updateGroup = (updateForm: GroupUpdateFormDTO) => {
  return http.request<Result<GroupVO>>("put", "/admin/groups", {data: updateForm});
};

export const deleteGroup = (id: string) => {
  return http.request<Result<void>>("delete", `/admin/groups/${id}`);
};


