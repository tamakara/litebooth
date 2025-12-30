import {defineStore} from 'pinia'
import {ref} from 'vue'
import {fetchItemInfoVO as fetchItemInfoVOApi} from "@/api/item";
import {createOrder as createOrderApi, payOrder as payOrderApi} from "@/api/order";
import {fetchCaptchaInfoVO as fetchCaptchaInfoVOApi, verifyCaptchaCode as verifyCaptchaCodeApi} from "@/api/captcha";
import {OrderStatus, PaymentMethod} from "@/types/enums";
import {CaptchaInfoVO, ItemInfoVO, OrderCreateFormDTO, OrderInfoVO} from "@/types";

export const useItemStore = defineStore('item', () => {
    const itemInfo = ref<ItemInfoVO>({
        id: 0,
        name: '',
        coverFileUrl: '',
        price: 0.00,
        stock: 0,
        description: '',
    })
    const captchaInfo = ref<CaptchaInfoVO>({
        captchaKey: '',
        imageBase64: '',
    })
    const orderCreateForm = ref<OrderCreateFormDTO>({
        itemId: 0,
        quantity: 1,
        paymentMethod: PaymentMethod.WXPAY,
        queryEmail: '',
        queryPassword: '',
        captchaToken: '',
    })

    const orderInfo = ref<OrderInfoVO>({
        id: '',
        status: OrderStatus.UNPAID,
        queryEmail: '',
        queryPassword: '',
        itemName: '',
        itemPrice: 0,
        quantity: 0,
        amount: 0,
        paymentMethod: PaymentMethod.WXPAY,
        contentList: [],
        createdAt: '',
        paymentAt: '',
    })
    const loading = ref(false)

    const fetchCaptchaInfoVO = async () => {
        loading.value = true
        try {
            captchaInfo.value = await fetchCaptchaInfoVOApi()
        } finally {
            loading.value = false
        }
    }

    const verifyCaptchaCode = async () => {
        const token = await verifyCaptchaCodeApi({
            captchaKey: captchaInfo.value.captchaKey,
            captchaCode: orderCreateForm.value.captchaCode || ''
        })

        if (token) {
            orderCreateForm.value.captchaToken = token
            return token
        }
        return false
    }

    const fetchItemInfoVO = async (itemId: string) => {
        loading.value = true
        try {
            itemInfo.value = await fetchItemInfoVOApi(itemId)
            orderCreateForm.value.itemId = itemInfo.value.id
        } finally {
            loading.value = false
        }
    }

    const createOrder = async () => {
        orderInfo.value = await createOrderApi(orderCreateForm.value)
    }

    const payOrder = async () => {
        orderInfo.value = await payOrderApi(orderInfo.value.id)
    }

    const clearOrderCreateForm = () => {
        orderCreateForm.value = {
            itemId: itemInfo.value.id,
            quantity: 1,
            paymentMethod: PaymentMethod.WXPAY,
            queryEmail: '',
            queryPassword: '',
            captchaToken: '',
            captchaCode: '',
        }
    }

    return {
        itemInfo,
        captchaInfo,
        orderCreateForm,
        orderInfo,
        loading,
        fetchCaptchaInfoVO,
        verifyCaptchaCode,
        fetchItemInfoVO,
        createOrder,
        payOrder,
        clearOrderCreateForm
    }
})
