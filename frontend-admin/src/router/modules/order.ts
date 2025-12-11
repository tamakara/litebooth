export default {
  path: "/order",
  meta: {
    icon: "ep/list",
    title: "订单管理",
    rank: 4,
  },
  children: [
    {
      path: "/order",
      name: "Order",
      component: () => import("@/views/order/index.vue"),
      meta: {
        title: "订单管理"
      }
    }
  ]
};
