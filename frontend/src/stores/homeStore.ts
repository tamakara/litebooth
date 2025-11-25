import {defineStore} from 'pinia'
import {fetchHomeInfoVO} from "@/api/home";
import {fetchItemCardListPageVO} from "@/api/item";

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
        itemCardListPage: {
            itemCardList: [] as ItemCardVO[],
            pageNum: 1,
            pageSize: 20,
            total: 0
        },
        loading: false
    }),
    getters: {},
    actions: {
        async fetchHomeInfo() {
            this.loading = true
            try {
                const res = await fetchHomeInfoVO()
                this.homeInfo = res.data
            } finally {
                this.loading = false
            }
        },
        async fetchItemCardListPageVO() {
            this.loading = true
            try {
                const res = await fetchItemCardListPageVO(this.itemCardQueryForm)
                this.itemCardListPage = res.data
            } finally {
                this.loading = false
            }
        }
    }
})
