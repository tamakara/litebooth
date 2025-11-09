import { defineStore } from 'pinia'

function genMockOrders() {
  const status = ['已支付', '待支付', '已发货', '已完成']
  return Array.from({ length: 12 }).map((_, i) => ({
    id: (i + 1).toString(),
    number: `LB${String(20251100 + i)}`,
    status: status[i % status.length],
    amount: Number((Math.random() * 500 + 20).toFixed(2)),
    user: {
      name: `用户${i + 1}`,
      email: `user${i + 1}@mail.com`,
      phone: `1380000${String(100 + i)}`,
    },
    createdAt: new Date(Date.now() - i * 86400000).toISOString(),
    items: Array.from({ length: (i % 3) + 1 }).map((__, j) => ({
      sku: `SKU-${i + 1}-${j + 1}`,
      name: `订单商品 ${i + 1}-${j + 1}`,
      price: Number((Math.random() * 200 + 10).toFixed(2)),
      qty: (j % 2) + 1,
      cover: `https://picsum.photos/seed/o${i}${j}/120/80`,
    }))
  }))
}

export const useOrderStore = defineStore('order', {
  state: () => ({ orders: genMockOrders(), keyword: '' }),
  getters: {
    filtered(state) {
      if (!state.keyword) return state.orders
      const kw = state.keyword.toLowerCase()
      return state.orders.filter(o =>
        o.number.toLowerCase().includes(kw) ||
        o.user.email.toLowerCase().includes(kw) ||
        o.user.phone.toLowerCase().includes(kw)
      )
    },
    byId: (state) => (id) => state.orders.find(o => o.id === id),
  },
  actions: {
    setKeyword(v) { this.keyword = v }
  }
})

