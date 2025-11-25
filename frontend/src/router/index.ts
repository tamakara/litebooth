import {createRouter, createWebHistory} from 'vue-router'

import Home from '@/views/Home.vue'
import User from '@/views/User.vue'
import Item from '@/views/Item.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', name: 'home', component: Home},
        {path: '/user', name: 'user', component: User},
        {path: '/item/:id', name: 'item', component: Item},
        {path: '/:pathMatch(.*)*', redirect: '/'},
    ],
    scrollBehavior() {
        return {top: 0}
    },
})

export default router
