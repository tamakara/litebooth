import { defineStore } from 'pinia'

// 临时 mock 商品数据，后续可由后端接口替换
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

export const useHomeStore = defineStore('home', {
  state: () => ({
    // 页面整体数据封装，方便后端一体化返回
    pageData: {
      announcement: {
        content: ''
      },
      header: {
        title: '发现好物，享受生活',
        subtitle: '简洁优雅的小店页面示例，支持商家自定义分组浏览。'
      },
      searchForm: {
        keyword: '',
        group: '',
        // 可扩展其他查询条件
        // priceMin: null,
        // priceMax: null
      }
    },

    // 商品相关状态
    loading: false,
    items: [],
    keyword: '',
    groupSelected: '全部'
  }),
  getters: {
    // 商品分组列表
    groups(state) {
      return ['全部', ...new Set(state.items.map(it => it.group))]
    },
    // 过滤后的商品列表
    filteredItems(state) {
      return state.items.filter(it => {
        const realKeyword = state.pageData.searchForm.keyword || state.keyword
        const realGroup = state.pageData.searchForm.group || state.groupSelected

        const kwOk = !realKeyword || it.name.toLowerCase().includes(realKeyword.toLowerCase())
        const grpOk = realGroup === '全部' || it.group === realGroup
        return kwOk && grpOk
      })
    }
  },
  actions: {
    // 获取首页所有数据，后续可替换为实际 API 调用
    async fetchHomeData() {
      this.loading = true
      try {
        // TODO: 调用后端接口获取首页数据
        // const res = await api.getHomeData()
        // this.items = res.items
        // this.pageData = res.pageData

        // 目前仍使用 mock 数据
        this.items = mockItems()
      } finally {
        this.loading = false
      }
    },
    // 以下为原 itemStore 中保留的有用 action
    setKeyword(v) {
      this.keyword = v
      this.pageData.searchForm.keyword = v
    },
    setGroup(v) {
      this.groupSelected = v
      this.pageData.searchForm.group = v
    }
  }
})

