import {OrderStatus, PaymentMethod} from "@/types/enums/order";

export interface OrderCreateFormDTO {
    itemId: number;
    quantity: number;
    paymentMethod: PaymentMethod;
    queryEmail: string;
    queryPassword: string;
    captchaToken: string;
}

export interface OrderInfoVO {
    id: string;
    status: OrderStatus;
    queryEmail: string;
    queryPassword: string;
    itemName: string;
    itemPrice: number;
    quantity: number;
    amount: number;
    paymentMethod: PaymentMethod;
    contentList: string[];
    createdAt: string;
    paymentAt: string;
}

export interface OrderInfoPageVO {
    records: OrderInfoVO[];
    pageNum: number;
    pageSize: number;
    total: number;
}

export interface OrderInfoPageQueryFormDTO {
    queryMode: string;
    orderId: string;
    queryEmail: string;
    queryPassword: string;
    pageNum: number;
    pageSize: number;
}
