declare interface HomeVO {
    title: string;
    subtitle: string;
    announcement: string;
    groups: string[];
}

declare interface ItemCardQueryFormDTO {
    keyword: string;
    group: string;
    pageNum: number;
    pageSize: number;
}

declare interface ItemCardVO {
    id: number;
    name: string;
    price: number;
    group: string;
    cover: string;
}

declare interface ItemCardListPageVO {
    itemCardList: ItemCardVO[];
    pageNum: number;
    pageSize: number;
    total: number;
}

declare interface ItemInfoVO {
    id: string;
    name: string;
    group: string;
    cover: string;
    price: number;
    stock: number;
    description: string;
}

declare interface OrderQueryFormDTO {
    itemId: string;
    quantity: number;
    payMethod: string;
}

declare interface OrderInfo {
    id: string;
    status: string;
    userMail: string;
    itemName: string;
    itemPrice: number;
    quantity: number;
    amount: number;
    payMethod: string;
    contentList: string[];
    createdAt: string;
    updatedAt: string;
}


