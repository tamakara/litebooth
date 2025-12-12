import {defineStore} from 'pinia'
import {fetchShopInfoVO} from "@/api/shop";
import {fetchGroupListVO, fetchItemCardPageVO} from "@/api/item";
import {ItemCardVO} from "@/types";

export const useHomeStore = defineStore('home', {
    state: () => ({
        shopInfo: {
            logo: '',
            logoTitle: '',
            homeTitle: '',
            homeSubtitle: '',
            homeAnnouncement: '',
        },
        groupList: ['全部'],
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
        async fetchShopInfoVO() {
            this.loading = true
            try {
                const res = await fetchShopInfoVO()
                this.shopInfo = res.data
            } finally {
                this.loading = false
            }
        },
        async fetchGroupListVO() {
            this.loading = true
            try {
                const res = await fetchGroupListVO()
                this.groupList = res.data
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
