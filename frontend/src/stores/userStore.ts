import {defineStore} from 'pinia'
import {getOrderListVO, getProfileVO, login, register, updateAvatar} from "@/api/user.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        token: '',
        profile: {
            username: '',
            email: '',
            avatar: '',
        },
        orderList: {
            orders: [],
            pageNumber: 1,
            pageSize: 10,
            total: 0,
        },
        searchForm: {
            pageNumber: 1,
            pageSize: 10,
        },
        loading: false,
        error: ''
    }),
    getters: {
        isLogin: (s) => !!s.token,
        username: (s) => s.profile?.username || '未登录'
    },
    actions: {
        async login(form) {
            this.loading = true
            this.error = ''
            await new Promise(r => setTimeout(r, 400))

            const res = await login(form)

            this.token = res.data.token
            this.profile = res.data.profile
            localStorage.setItem('token', this.token)

            this.loading = false
        },

        async register(form) {
            this.loading = true
            this.error = ''
            await new Promise(r => setTimeout(r, 500))


            const res = await register(form)
            this.token = res.data.token
            this.profile = res.data.profile
            localStorage.setItem('token', this.token)

            this.loading = false
        },

        async restore() {
            if (this.token) return
            const token = localStorage.getItem('token')
            if (token) {
                this.token = token
                const res = await getProfileVO()
                this.profile = res.data
            }
        },

        async getOrderList() {
            const res = await getOrderListVO(this.searchForm)
            this.orderList = res.data
        },

        async updateAvatar(newAvatarFile) {
            const res = await updateAvatar(newAvatarFile)
            this.profile.avatar = res.data
        },

        updateEmail(newEmail) {

        },

        logout() {
            this.token = ''
            this.profile = null
        }
    }
})
