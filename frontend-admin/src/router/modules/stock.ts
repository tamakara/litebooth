export default {
  path: "/stock",
  meta: {
    icon: "ep/box",
    title: "仓库管理",
    rank: 4,
  },
  children: [
    {
      path: "/stock",
      name: "Stock",
      component: () => import("@/views/stock/index.vue"),
      meta: {
        title: "仓库管理"
      }
    }
  ]
};
