export interface ItemCardPageQueryFormDTO {
    keyword: string;
    groupId: number;
    pageNum: number;
    pageSize: number;
}

export interface ItemCardVO {
    id: number;
    name: string;
    price: number;
    cover: string;
}

export interface ItemCardPageVO {
    records: ItemCardVO[];
    pageNum: number;
    pageSize: number;
    total: number;
}

export interface ItemInfoVO {
    id: number;
    name: string;
    cover: string;
    price: number;
    stock: number;
    description: string;
}

export interface GroupVO{
    id: number;
    name: string;
}

