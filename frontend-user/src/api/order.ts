import http from "@/api/http";
import {OrderCreateFormDTO, OrderInfoPageQueryFormDTO, OrderInfoPageVO, OrderInfoVO} from "@/types";

export function createOrder(form: OrderCreateFormDTO): Promise<OrderInfoVO> {
    return http.post('/user/orders', form)
}

export function payOrder(orderId: string): Promise<OrderInfoVO> {
    return http.post(`/user/orders/${orderId}/pay`)
}

export function fetchOrderInfoPageVO(form: OrderInfoPageQueryFormDTO): Promise<OrderInfoPageVO> {
    return http.get('/user/orders', {params: form})
}
