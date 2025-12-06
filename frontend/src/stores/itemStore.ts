import {defineStore} from 'pinia'
import {fetchItemInfoVO} from "@/api/item";
import {createOrder, payOrder} from "@/api/order";
import {fetchCaptchaInfoVO} from "@/api/captcha";
import {OrderStatus, PaymentMethod} from "@/types/enums";

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
        captchaInfo: {
            captchaKey: '',
            imageBase64: '',
        },
        orderCreateForm: {
            itemId: '',
            quantity: 1,
            paymentMethod: PaymentMethod.WXPAY,
            queryEmail: '',
            queryPassword: '',
            captchaKey: '',
            captchaCode: '',
        },
        orderInfo: {
            id: '',
            status: OrderStatus.UNPAID,
            queryEmail: '',
            queryPassword: '',
            itemName: '',
            itemPrice: 0,
            quantity: 0,
            amount: 0,
            paymentMethod: PaymentMethod.WXPAY,
            contentList: null,
            createdAt: '',
            paymentAt: null,
        },
        loading: false,
    }),
    getters: {},
    actions: {
        async fetchCaptchaInfoVO() {
            this.loading = true
            try {
                const res = await fetchCaptchaInfoVO()
                this.captchaInfo = res.data
                this.orderCreateForm.captchaKey = res.data.captchaKey
            } finally {
                this.loading = false
            }
        },

        async fetchItemInfoVO(itemId: string) {
            this.loading = true
            try {
                const res = await fetchItemInfoVO(itemId)
                this.itemInfo = res.data
                this.orderCreateForm.itemId = res.data.id
            } finally {
                this.loading = false
            }
        },

        async createOrder() {
            const res = await createOrder(this.orderCreateForm)
            this.orderInfo = res.data
        },

        async payOrder() {
            const res = await payOrder(this.orderInfo.id)
            this.orderInfo = res.data
        },

        clearOrderCreateForm() {
            this.orderCreateForm = {
                itemId: '',
                quantity: 1,
                paymentMethod: PaymentMethod.WXPAY,
                queryEmail: '',
                queryPassword: '',
                captchaKey: '',
                captchaCode: '',
            }
        },

    }
})
