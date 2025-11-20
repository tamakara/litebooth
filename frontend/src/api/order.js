import http from "./http.js";

export function createOrder(orderForm) {
    return http.post('/order/createOrder', orderForm)
}

