import {defineStore} from 'pinia'
import {fetchItemInfoVO} from "@/api/item";
import { createOrder, payOrder, cancelOrder } from "@/api/order";

export const useItemStore = defineStore('item', {
    state: () => ({
        itemInfo: {
            id: '',
            name: '',
            group: '',
            cover: '',
            price: 0.00,
            stock: 0,
            description: '',
        },
        orderCreateForm: {
            itemId: '',
            quantity: 1,
            payMethod: 'wxpay',
            // 新增字段：收货邮箱、订单查询密码、图形验证码
            email: '',
            queryPassword: '',
            captchaCode: '',
        },
        orderInfo: {
            id: '',
            status: '未付款',
            userMail: '',
            itemName: '',
            itemPrice: 0,
            quantity: 0,
            amount: 0,
            payMethod: 'wxpay',
            createdAt: '',
        },
        loading: false,
    }),
    getters: {},
    actions: {
        async fetchItemInfoVO(itemId: string) {
            this.loading = true
            try {
                const res = await fetchItemInfoVO(itemId)
                this.itemInfo = res.data
                this.orderCreateForm.itemId = this.itemInfo.id
            } finally {
                this.loading = false
            }
        },

        async createOrder() {
            const res = await createOrder(this.orderCreateForm)
            this.orderInfo = res.data
        },

        async payOrder() {
            await payOrder(this.orderInfo.id)
        }
    }
})
