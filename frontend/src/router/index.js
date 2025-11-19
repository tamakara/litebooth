import { createRouter, createWebHistory } from 'vue-router'

import Home from '../pages/Home.vue'
import User from '../pages/User.vue'
import Item from '../pages/Item.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: Home },
    { path: '/user', name: 'user', component: User },
    { path: '/item/:id', name: 'item', component: Item },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
  scrollBehavior() { return { top: 0 } },
})

export default router
