import {defineStore} from 'pinia'
import {fetchShopInfoVO} from "@/api/shop";
import {fetchItemCardPageVO} from "@/api/item";
import {fetchGroupListVO} from "@/api/group";
import {GroupVO, ItemCardVO} from "@/types";

export const useHomeStore = defineStore('home', {
    state: () => ({
        shopInfo: {
            logoFileId: 0,
            logoFileUrl: '',
            logoTitle: '',
            homeTitle: '',
            homeSubtitle: '',
            homeAnnouncement: '',
        },
        groupList: [] as GroupVO[],
        itemCardQueryForm: {
            keyword: '',
            groupId: 0,
            pageNum: 1,
            pageSize: 20
        },
        itemCardPage: {
            records: [] as ItemCardVO[],
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
