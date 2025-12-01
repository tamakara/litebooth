import {defineStore} from 'pinia'
import {fetchOrderInfoPageVO, payOrder as payOrderApi} from "@/api/order";

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
            queryMode: 'orderId',
            orderId: '',
            queryEmail: '',
            queryPassword: '',
            pageNum: 1,
            pageSize: 10,
        } as OrderInfoPageQueryFormDTO,
        loading: false,
    }),
    getters: {},
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
        async payExistingOrder(orderId: string) {
            this.loading = true
            try {
                await payOrderApi(orderId)
                await this.fetchOrderInfoPageVO()
            } finally {
                this.loading = false
            }
        },
        resetQueryForm() {
            this.queryForm.pageNum = 1
            this.queryForm.pageSize = 10
            this.queryForm.orderId = ''
            this.queryForm.queryEmail = ''
            this.queryForm.queryPassword = ''
        }

    }
})
