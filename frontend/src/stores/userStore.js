import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    profile: null, // { name, email, avatar?, phone? }
    loading: false,
    error: ''
  }),
  getters: {
    isLogin: (s) => !!s.token,
    username: (s) => s.profile?.name || '未登录'
  },
  actions: {
    async login(form) {
      this.loading = true
      this.error = ''
      await new Promise(r => setTimeout(r, 400))
      if (form.username && form.password) {
        this.token = 'mock-token-' + Date.now()
        this.profile = { name: form.username, email: form.username + '@mail.com', avatar: '', phone: '' }
      } else {
        this.error = '请输入有效凭据'
      }
      this.loading = false
    },
    async register(form) {
      this.loading = true
      this.error = ''
      await new Promise(r => setTimeout(r, 500))
      if (form.username && form.password) {
        this.token = 'mock-token-' + Date.now()
        this.profile = { name: form.username, email: form.email || (form.username + '@mail.com'), avatar: '', phone: '' }
      } else {
        this.error = '注册信息不完整'
      }
      this.loading = false
    },
    updateProfile(payload) {
      if (!this.profile) return
      this.profile = { ...this.profile, ...payload }
    },
    logout() {
      this.token = ''
      this.profile = null
    }
  }
})
