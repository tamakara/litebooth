import {http} from "@/utils/http";
import {
  GroupListVO,
  GroupMapVO,
  GroupPageQueryFormDTO,
  GroupPageVO,
  GroupUpdateFormDTO,
  GroupVO
} from "@/views/group/utils/types";

export const getGroupListVO = () => {
  return http.request<GroupListVO>("get", "/group/getGroupListVO");
};

export const getGroupMapVO = () => {
  return http.request<GroupMapVO>("get", "/group/getGroupMapVO");
};

export const getGroupPageVO = (queryForm: GroupPageQueryFormDTO) => {
  return http.request<GroupPageVO>("get", "/group/getGroupPageVO", {params: queryForm});
};

export const createGroup = (name: string) => {
  return http.request<GroupVO>("post", `/group/createGroup/${name}`);
};

export const updateGroup = (updateForm: GroupUpdateFormDTO) => {
  return http.request<GroupVO>("put", "/group/updateGroup", {data: updateForm});
};

export const deleteGroup = (id: string) => {
  return http.request<void>("delete", `/group/deleteGroup/${id}`);
};


