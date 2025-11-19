import {defineStore} from 'pinia'
import {getHomeInfoVO} from "@/api/home.js";
import {getItemCardListVO} from "../api/item.js";

export const useHomeStore = defineStore('home', {
    state: () => ({
        homeInfo: {
            announcement: '',
            title: '',
            subtitle: '',
            groups: ['全部'],
        },
        searchForm: {
            keyword: '',
            group: '全部',
            pageNumber: 1,
            pageSize: 20
        },
        itemCardList: {
            items: [
                {
                    id: 1,
                    name: '示例商品 1',
                    price: 19.9,
                    cover: 'https://picsum.photos/seed/i1/300/200',
                    group: '1',
                },
                {
                    id: 2,
                    name: '示例商品 2',
                    price: 29.9,
                    cover: 'https://picsum.photos/seed/i2/300/200',
                    group: '1',
                },
                {
                    id: 3,
                    name: '示例商品 3',
                    price: 39.9,
                    cover: 'https://picsum.photos/seed/i3/300/200',
                    group: '2',
                }
            ],
            pageNumber: 1,
            pageSize: 20,
            total: 100
        },
        loading: false
    }),
    getters: {},
    actions: {
        async fetchHomeInfo() {
            this.loading = true
            try {
                const response = await getHomeInfoVO()
                this.homeInfo = response.data

            } finally {
                this.loading = false
            }
        },
        async fetchItemInfo() {
            this.loading = true
            try {
                const response = await getItemCardListVO(this.searchForm)
                this.itemCardList = response.data
            } finally {
                this.loading = false
            }
        }
    }
})
