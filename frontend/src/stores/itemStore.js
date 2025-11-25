import {defineStore} from 'pinia'
import {getItemVO} from "@/api/item.js";
import {cancelOrder, createOrder, payOrder} from "@/api/order.js";

export const useItemStore = defineStore('item', {
    state: () => ({
        loading: false,
        itemInfo: {
            id: 1,
            name: '示例商品',
            group: '1',
            cover: 'https://via.placeholder.com/480x320.png?text=Item+Cover',
            price: 19.9,
            stock: 50,
            description: '<h1>示例商品介绍：这是一个用于演示的商品详情页面。</h1>',
        },
        orderForm: {
            itemId: 1,
            quantity: 1,
            payMethod: 'wxpay'
        },
        orderInfo: {
            id: 1,
            status: '未付款',
            userMail: '',
            itemName: '示例商品',
            itemPrice: 19.98,
            quantity: 1,
            totalPrice: 19.98,
            payMethod: 'wxpay',
            createdAt: '2024-06-01 12:00:00',
        }
    }),
    getters: {},
    actions: {
        async fetchItem(itemId) {
            this.loading = true
            try {
                const response = await getItemVO(itemId)
                this.itemInfo = response.data
                this.orderForm.itemId = this.itemInfo.id
            } catch (error) {
                console.error('Failed to fetch item info:', error)
            } finally {
                this.loading = false
            }
        },

        async createOrder() {
            const response = await createOrder(this.orderForm)
            this.orderInfo = response.data
        },

        async cancelOrder() {
            await cancelOrder(this.orderInfo.id)
        },

        async payOrder() {
            await payOrder(this.orderInfo.id)
        }
    }
})
