import {defineStore} from 'pinia'
import {ref} from 'vue'
import {fetchOrderInfoPageVO as fetchOrderInfoPageVOApi, payOrder as payOrderApi} from "@/api/order";
import {OrderInfoPageQueryFormDTO, OrderInfoPageVO} from "@/types";

export const useOrderStore = defineStore('order', () => {
    const orderInfoPage = ref<OrderInfoPageVO>({
        records: [],
        pageNum: 1,
        pageSize: 10,
        total: 0,
    })
    const queryForm = ref<OrderInfoPageQueryFormDTO>({
        queryMode: 'orderId',
        orderId: '',
        queryEmail: '',
        queryPassword: '',
        pageNum: 1,
        pageSize: 10,
    })
    const loading = ref(false)

    const fetchOrderInfoPageVO = async () => {
        loading.value = true
        try {
            orderInfoPage.value = await fetchOrderInfoPageVOApi(queryForm.value)
        } finally {
            loading.value = false
        }
    }

    const payExistingOrder = async (orderId: string) => {
        loading.value = true
        try {
            await payOrderApi(orderId)
            await fetchOrderInfoPageVO()
        } finally {
            loading.value = false
        }
    }

    const resetQueryForm = () => {
        queryForm.value = {
            queryMode: 'orderId',
            orderId: '',
            queryEmail: '',
            queryPassword: '',
            pageNum: 1,
            pageSize: 10,
        }
    }

    return {
        orderInfoPage,
        queryForm,
        loading,
        fetchOrderInfoPageVO,
        payExistingOrder,
        resetQueryForm
    }
})
