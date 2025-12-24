export interface ItemFormProps {
  id?: number;
  name: string;
  price: number;
  group: string;
  cover: number;
  description: string;
  isActive: boolean;
}

export interface ItemProps {
  formInline: ItemFormProps;
  groupOptions: any[];
}

export interface ItemVO {
  id: string;
  name: string;
  price: number;
  group: string;
  cover: string;
  coverUrl: string;
  description: string;
  isActive: boolean;
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

