import type {GroupListVO} from "@/views/group/utils/types";

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

export interface ItemProps {
  formInline: ItemCreateOrUpdateFormDTO;
  groupList: GroupListVO;
  coverFileUrl: string;
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
}

export interface ItemPageVO {
  records: ItemVO[];
  pageNum: number;
  pageSize: number;
  total: number;
}

