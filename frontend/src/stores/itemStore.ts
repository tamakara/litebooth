import {defineStore} from 'pinia'
import {fetchItemInfoVO} from "@/api/item";
import {cancelOrder, createOrder, payOrder} from "@/api/order";

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
        orderQueryForm: {
            itemId: '',
            quantity: 1,
            payMethod: 'wxpay'
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
                this.orderQueryForm.itemId = this.itemInfo.id
            } finally {
                this.loading = false
            }
        },

        async createOrder() {
            const res = await createOrder(this.orderQueryForm)
            this.orderInfo = res.data
        },

        async cancelOrder() {
            await cancelOrder(this.orderInfo.id)
        },

        async payOrder() {
            await payOrder(this.orderInfo.id)
        }
    }
})
