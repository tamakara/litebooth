export interface FormItemProps {
  id?: string;
  name: string;
}

export interface FormProps {
  formInline: FormItemProps;
}

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

export interface GroupPageVO {
  records: GroupListVO;
  pageNum: number;
  pageSize: number;
  total: number;
}

export type GroupListVO = GroupVO[];

export type GroupMapVO = Record<number, string>;
