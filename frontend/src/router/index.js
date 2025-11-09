import { createRouter, createWebHistory } from 'vue-router'

import Home from '../pages/Home.vue'
import Orders from '../pages/Orders.vue'
import OrderDetail from '../pages/OrderDetail.vue'
import User from '../pages/User.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: Home },
    { path: '/orders', name: 'orders', component: Orders },
    { path: '/order/:id', name: 'order-detail', component: OrderDetail, props: true },
    { path: '/user', name: 'user', component: User },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
  scrollBehavior() { return { top: 0 } },
})

export default router
