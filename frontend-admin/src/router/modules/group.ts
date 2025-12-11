export default {
  path: "/group",
  meta: {
    icon: "ep/menu",
    title: "商品组管理",
    rank: 2,
  },
  children: [
    {
      path: "/group",
      name: "Group",
      component: () => import("@/views/group/index.vue"),
      meta: {
        title: "商品组管理"
      }
    }
  ]
};
