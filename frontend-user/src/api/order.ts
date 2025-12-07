import http from "@/api/http";
import {AxiosResponse} from "axios";
import {OrderCreateFormDTO, OrderInfoPageQueryFormDTO, OrderInfoPageVO, OrderInfoVO} from "@/types";

export function createOrder(form: OrderCreateFormDTO): Promise<AxiosResponse<OrderInfoVO>> {
    return http.post('/order/createOrder', form)
}

export function payOrder(orderId: string): Promise<AxiosResponse<OrderInfoVO>> {
    return http.post(`/order/payOrder/${orderId}`)
}

export function fetchOrderInfoPageVO(form: OrderInfoPageQueryFormDTO): Promise<AxiosResponse<OrderInfoPageVO>> {
    return http.get('/order/vo/OrderInfoPageVO', {params: form})
}
