import { defineStore } from 'pinia'

function mockItems() {
  const groups = ['组1', '组2', '组3']
  return Array.from({ length: 24 }).map((_, i) => ({
    id: i + 1,
    name: `示例商品 ${i + 1}`,
    price: (Math.random() * 200 + 10).toFixed(2),
    cover: `https://picsum.photos/seed/i${i}/300/200`,
    group: groups[i % groups.length],
    desc: '这是一段商品简介，占位文字用于展示效果。'
  }))
}

export const useItemStore = defineStore('item', {
  state: () => ({
    loading: false,
    items: [],
    keyword: '',
    groupSelected: '全部'
  }),
  getters: {
    filteredItems(state) {
      return state.items.filter(it => {
        const kwOk = !state.keyword || it.name.toLowerCase().includes(state.keyword.toLowerCase())
        const grpOk = state.groupSelected === '全部' || it.group === state.groupSelected
        return kwOk && grpOk
      })
    },
    groups() { return ['全部', ...new Set(this.items.map(it => it.group))] }
  },
  actions: {
    async fetchItems() {
      this.loading = true
      await new Promise(r => setTimeout(r, 400))
      this.items = mockItems()
      this.loading = false
    },
    setKeyword(v) { this.keyword = v },
    setGroup(v) { this.groupSelected = v }
  }
})

