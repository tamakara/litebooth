export default {
  path: "/shop",
  meta: {
    icon: "ep/shop",
    title: "店铺管理",
    rank: 1,
  },
  children: [
    {
      path: "/shop",
      name: "Shop",
      component: () => import("@/views/shop/index.vue"),
      meta: {
        title: "店铺管理"
      }
    }
  ]
};
