export default {
  path: "/admin",
  meta: {
    icon: "ep/lock",
    title: "后台管理",
    rank: 5,
  },
  children: [
    {
      path: "/admin",
      name: "Admin",
      component: () => import("@/views/admin/index.vue"),
      meta: {
        title: "后台管理"
      }
    }
  ]
};
