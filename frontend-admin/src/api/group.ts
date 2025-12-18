import {http} from "@/utils/http";
import {GroupPageQueryFormDTO, GroupPageVO, GroupUpdateFormDTO, GroupVO} from "@/views/group/utils/types";


export const getGroupList = (queryForm: GroupPageQueryFormDTO) => {
  return http.request<GroupPageVO>("get", "/admin/getOrderInfoPageVO", {params: queryForm});
};

export const createGroup = (name: string) => {
  return http.request<GroupVO>("post", `/admin/createGroup/${name}`);
};

export const updateGroup = (updateForm: GroupUpdateFormDTO) => {
  return http.request<GroupVO>("put", "/admin/updateGroup", {data: updateForm});
};

export const deleteGroup = (id: string) => {
  return http.request<void>("delete", `/admin/deleteGroup/${id}`);
};
