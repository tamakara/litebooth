<script setup>
import HeaderBar from './components/HeaderBar.vue'
import {onMounted} from "vue";
import {useUserStore} from "@/stores/userStore.ts";

const user = useUserStore()

onMounted(() => {
  user.restore()
})
</script>

<template>
  <HeaderBar/>
  <router-view/>
</template>

<style scoped>

</style>

<style>

</style>
