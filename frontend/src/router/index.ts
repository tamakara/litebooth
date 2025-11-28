import {createRouter, createWebHistory} from 'vue-router'

import Home from '@/views/Home.vue'
import Order from '@/views/Order.vue'
import Item from '@/views/Item.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', name: 'home', component: Home},
        {path: '/item/:id', name: 'item', component: Item},
        {path: '/order', name: 'order', component: Order},
        {path: '/:pathMatch(.*)*', redirect: '/'},
    ],
    scrollBehavior() {
        return {top: 0}
    },
})

export default router
