import {defineStore} from 'pinia'
import {fetchHomeInfoVO} from "@/api/home";
import {fetchItemCardListPageVO, fetchItemCardPageVO} from "@/api/item";

export const useHomeStore = defineStore('home', {
    state: () => ({
        homeInfo: {
            title: '',
            subtitle: '',
            announcement: '',
            groups: ['全部'],
        },
        itemCardQueryForm: {
            keyword: '',
            group: '全部',
            pageNum: 1,
            pageSize: 20
        },
        itemCardPage: {
            itemCardList: [] as ItemCardVO[],
            pageNum: 1,
            pageSize: 20,
            total: 0
        },
        loading: false
    }),
    getters: {},
    actions: {
        async fetchHomeInfoVO() {
            this.loading = true
            try {
                const res = await fetchHomeInfoVO()
                this.homeInfo = res.data
            } finally {
                this.loading = false
            }
        },
        async fetchItemCardPageVO() {
            this.loading = true
            try {
                const res = await fetchItemCardPageVO(this.itemCardQueryForm)
                this.itemCardPage = res.data
            } finally {
                this.loading = false
            }
        }
    }
})
