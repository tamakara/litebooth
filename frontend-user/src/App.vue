<script setup>
import HeaderBar from './components/HeaderBar.vue'
import {onMounted} from "vue";
import {useHomeStore} from "@/store/homeStore";

const home = useHomeStore()

onMounted(async () => {
  await home.fetchShopInfoVO()
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
