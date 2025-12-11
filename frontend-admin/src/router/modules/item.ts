export default {
  path: "/item",
  meta: {
    icon: "ep/goods-filled",
    title: "商品管理",
    rank: 3,
  },
  children: [
    {
      path: "/item",
      name: "Item",
      component: () => import("@/views/item/index.vue"),
      meta: {
        title: "商品管理"
      }
    }
  ]
};
