import {defineStore} from 'pinia'
import {login, register} from "@/api/user.js";

export const useUserStore = defineStore('user', {
    state: () => ({
        token: '',
        profile: null, // { username, email, avatar}
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
            console.log(res)
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

        updateProfile(payload) {
            if (!this.profile) return
            this.profile = {...this.profile, ...payload}
        },

        logout() {
            this.token = ''
            this.profile = null
        }
    }
})
