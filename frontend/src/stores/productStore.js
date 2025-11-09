import { defineStore } from 'pinia'

function mockProducts() {
  const groups = ['组1', '组2', '组3']
  return Array.from({ length: 24 }).map((_, i) => ({
    id: i + 1,
    name: `示例商品 ${i + 1}`,
    price: (Math.random() * 200 + 10).toFixed(2),
    cover: `https://picsum.photos/seed/p${i}/300/200`,
    group: groups[i % groups.length],
    desc: '这是一段商品简介，占位文字用于展示效果。'
  }))
}

export const useProductStore = defineStore('product', {
  state: () => ({
    loading: false,
    products: [],
    keyword: '',
    groupSelected: '全部'
  }),
  getters: {
    filteredProducts(state) {
      return state.products.filter(p => {
        const kwOk = !state.keyword || p.name.toLowerCase().includes(state.keyword.toLowerCase())
        const grpOk = state.groupSelected === '全部' || p.group === state.groupSelected
        return kwOk && grpOk
      })
    },
    groups() { return ['全部', ...new Set(this.products.map(p => p.group))] }
  },
  actions: {
    async fetchProducts() {
      this.loading = true
      await new Promise(r => setTimeout(r, 400))
      this.products = mockProducts()
      this.loading = false
    },
    setKeyword(v) { this.keyword = v },
    setGroup(v) { this.groupSelected = v }
  }
})
