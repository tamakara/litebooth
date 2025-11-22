import http from "./http.js";

export function createOrder(orderForm) {
    return http.post('/order/createOrder', orderForm)
}

export function cancelOrder(orderId) {
    return http.delete(`/order/cancelOrder/${orderId}`)
}

