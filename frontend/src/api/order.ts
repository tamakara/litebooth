import http from "@/api/http";
import {AxiosResponse} from "axios";

export function createOrder(form: OrderQueryFormDTO): Promise<AxiosResponse<OrderInfo>> {
    return http.post('/order/createOrder', form)
}

export function cancelOrder(orderId: string) {
    return http.delete(`/order/cancelOrder/${orderId}`)
}

export function payOrder(orderId: string) {
    return http.post(`/order/payOrder/${orderId}`)
}
