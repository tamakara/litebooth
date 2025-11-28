import {defineStore} from 'pinia'
import {fetchOrderInfoPageVO} from "@/api/order";

export const orderStore = defineStore('order', {
    state: () => ({
        token: '',
        profile: {
            username: '',
            email: '',
            avatar: '',
        },
        orderInfoPage: {
            orderInfoList: [] as OrderInfoVO[],
            pageNum: 1,
            pageSize: 10,
            total: 0,
        },
        queryForm: {
            pageNum: 1,
            pageSize: 10,
        },
        loading: false,
    }),
    getters: {
    },
    actions: {
        async fetchOrderInfoPageVO() {
            this.loading = true
            try {
                const res = await fetchOrderInfoPageVO(this.queryForm)
                this.orderInfoPage = res.data
            } finally {
                this.loading = false
            }
        },

    }
})
