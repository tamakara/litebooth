import {defineStore} from 'pinia'
import { getItemInfoVO} from "@/api/item.js";

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
        buyForm: {
            qty: 1,
            payType: 'wechat'
        }
    }),
    getters: {},
    actions: {
        async fetchItemInfo(itemId) {
            this.loading = true
            try {
                const response = await getItemInfoVO(itemId)
                this.itemInfo = response.data
            } catch (error) {
                console.error('Failed to fetch item info:', error)
            } finally {
                this.loading = false
            }
        }
    }
})
