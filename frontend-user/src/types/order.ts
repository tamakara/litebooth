import {OrderStatus, PaymentMethod} from "@/types/enums/order";

export interface OrderCreateFormDTO {
    itemId: string;
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
    contentList: string[] | null;
    createdAt: string;
    paymentAt: string | null;
}

export interface OrderInfoPageVO {
    orderInfoList: OrderInfoVO[];
    pageNum: number;
    pageSize: number;
    total: number;
}

export interface OrderInfoPageQueryFormDTO {
    queryMode: string;
    orderId?: string;
    queryEmail?: string;
    queryPassword?: string;
    pageNum: number;
    pageSize: number;
}
