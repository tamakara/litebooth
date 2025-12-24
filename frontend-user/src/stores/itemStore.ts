import {defineStore} from 'pinia'
import {fetchItemInfoVO} from "@/api/item";
import {createOrder, payOrder} from "@/api/order";
import {fetchCaptchaInfoVO, verifyCaptchaCode} from "@/api/captcha";
import {OrderStatus, PaymentMethod} from "@/types/enums";

export const useItemStore = defineStore('item', {
    state: () => ({
        itemInfo: {
            id: 0,
            name: '',
            coverFileUrl: '',
            price: 0.00,
            stock: 0,
            description: '',
        },
        captchaInfo: {
            captchaKey: '',
            imageBase64: '',
        },
        orderCreateForm: {
            itemId: 0,
            quantity: 1,
            paymentMethod: PaymentMethod.WXPAY,
            queryEmail: '',
            queryPassword: '',
            captchaCode: '',
            captchaToken: '',
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
            contentList: [''],
            createdAt: '',
            paymentAt: '',
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
            } finally {
                this.loading = false
            }
        },

        async verifyCaptchaCode() {
            const res = await verifyCaptchaCode({
                captchaKey: this.captchaInfo.captchaKey,
                captchaCode: this.orderCreateForm.captchaCode
            })

            const captchaToken = res.data

            if (captchaToken) {
                this.orderCreateForm.captchaToken = captchaToken
                return true
            }

            return false
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
                itemId: 0,
                quantity: 1,
                paymentMethod: PaymentMethod.WXPAY,
                queryEmail: '',
                queryPassword: '',
                captchaCode: '',
                captchaToken: '',
            }
        },

    }
})
