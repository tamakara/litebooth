export interface ItemCardPageQueryFormDTO {
    keyword: string;
    group: string;
    pageNum: number;
    pageSize: number;
}

export interface ItemCardVO {
    id: number;
    name: string;
    price: number;
    group: string;
    cover: string;
}

export interface ItemCardPageVO {
    itemCardList: ItemCardVO[];
    pageNum: number;
    pageSize: number;
    total: number;
}

export interface ItemInfoVO {
    id: string;
    name: string;
    group: string;
    cover: string;
    price: number;
    stock: number;
    description: string;
}
