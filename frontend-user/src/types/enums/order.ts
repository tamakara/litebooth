export enum OrderStatus {
    UNPAID = 'UNPAID',
    EXPIRED = 'EXPIRED',
    FINISHED = 'FINISHED',
}

export const OrderStatusText = {
    UNPAID: '待付款',
    EXPIRED: '已过期',
    FINISHED: '已完成',
} as const

export enum PaymentMethod {
    WXPAY = 'WXPAY',
    ALIPAY = 'ALIPAY',
}

export const PaymentMethodText = {
    WXPAY: '微信支付',
    ALIPAY: '支付宝',
}