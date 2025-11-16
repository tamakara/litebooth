import {defineStore} from 'pinia'
import {getHomeInfoVO} from "@/api/home.js";

export const useHomeStore = defineStore('home', {
    state: () => ({
        homeInfo: {
            announcement: 'aaa',
            title: '发现好物，享受生活',
            subtitle: '简洁优雅的小店页面示例，支持商家自定义分组浏览。',
            groups: ['全部','1','2'],
        },
        searchForm: {
            keyword: '',
            groupName: '全部',
            pageNum: 1,
            pageSize: 20
        },
        itemData: {
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
                }
                , {
                    id: 3,
                    name: '示例商品 3',
                    price: 39.9,
                    cover: 'https://picsum.photos/seed/i3/300/200',
                    group: '2',
                }
            ]
        },
        loading: false
    }),
    getters: {

    },
    actions: {
        async fetchHomeInfo() {
            this.loading = true
            try {
               const response= await getHomeInfoVO()
                this.homeInfo = response.data

            } finally {
                this.loading = false
            }
        },
        async fetchItemData() {
            this.loading = true
            try {
                // TODO: 换成真实接口，例如：
                // const { data } = await api.getHomeData(this.itemData.searchForm)
                // this.pageData = data.pageData
                // this.itemData = data.itemData

            } finally {
                this.loading = false
            }
        }
    }
})
