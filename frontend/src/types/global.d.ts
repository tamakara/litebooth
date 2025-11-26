declare interface HomeInfoVO {
    title: string;
    subtitle: string;
    announcement: string;
    groups: string[];
}

declare interface ItemCardPageQueryFormDTO {
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

declare interface ItemCardPageVO {
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

declare interface OrderCreateFormDTO {
    itemId: string;
    quantity: number;
    payMethod: string;
}

declare interface OrderInfoVO {
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

declare interface LoginFormDTO {
    username: string;
    password: string;
}

declare interface RegisterFormDTO {
    username: string;
    password: string;
    email: string;
    code: string;
}

declare interface OrderInfoPageVO {
    orderInfoList: OrderInfoVO[];
    pageNum: number;
    pageSize: number;
    total: number;
}

declare interface OrderInfoPageQueryFormDTO {
    pageNum: number;
    pageSize: number;
}

declare interface ProfileVO {
    avatar: string;
    username: string;
    email: string;
}


