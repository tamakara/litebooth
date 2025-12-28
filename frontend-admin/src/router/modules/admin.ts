export default {
  path: "/admin",
  meta: {
    icon: "ep/setting",
    title: "后台管理",
    rank: 6,
  },
  children: [
    {
      path: "/admin",
      name: "Admin",
      component: () => import("@/views/admin/index.vue"),
      meta: {
        title: "后台设置"
      }
    }
  ]
};
