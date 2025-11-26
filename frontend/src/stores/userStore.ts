import {defineStore} from 'pinia'
import {fetchProfileVO, login, register, updateAvatar} from "@/api/user";
import {fetchOrderInfoPageVO} from "@/api/order";

export const useUserStore = defineStore('user', {
    state: () => ({
        token: '' as string,
        profile: {
            username: '',
            email: '',
            avatar: '',
        },
        orderInfoPage: {
            orderInfoList: [] as OrderInfoVO[],
            pageNum: 1,
            pageSize: 10,
            total: 0,
        },
        queryForm: {
            pageNum: 1,
            pageSize: 10,
        },
        loading: false,
    }),
    getters: {
        isLogin: (s) => !!s.token,
        username: (s) => s.profile?.username || '未登录'
    },
    actions: {
        async login(form: LoginFormDTO) {
            this.loading = true
            try {
                const res = await login(form)

                this.token = res.data.token
                this.profile = res.data.profile
                localStorage.setItem('token', this.token)
            } finally {
                this.loading = false
            }
        },

        async register(form: RegisterFormDTO) {
            this.loading = true
            try {
                const res = await register(form)
                this.token = res.data.token
                this.profile = res.data.profile
                localStorage.setItem('token', this.token)
            } finally {
                this.loading = false
            }
        },

        async restore() {
            if (this.token) return
            const token = localStorage.getItem('token')
            if (token) {
                this.token = token
                const res = await fetchProfileVO()
                this.profile = res.data
            }
        },

        async fetchOrderInfoPageVO() {
            this.loading = true
            try {
                const res = await fetchOrderInfoPageVO(this.queryForm)
                this.orderInfoPage = res.data
            } finally {
                this.loading = false
            }
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
