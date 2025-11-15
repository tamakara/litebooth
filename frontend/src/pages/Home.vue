<script setup>
import { onMounted, computed } from 'vue'
import { useHomeStore } from '../stores/homeStore'
import Announcement from '../components/Announcement.vue'
import ItemCard from '../components/ItemCard.vue'

const home = useHomeStore()

const pageData = computed(() => home.pageData)
const list = computed(() => home.filteredItems)
const groups = computed(() => home.groups)

onMounted(() => {
  if (!home.items.length) home.fetchHomeData()
})
</script>

<template>
  <Announcement
    v-if="pageData.announcement.content"
    :content="pageData.announcement.content"
  />

  <section class="hero">
    <h1>{{ pageData.header.title }}</h1>
    <p>{{ pageData.header.subtitle }}</p>
  </section>

  <div class="group-bar">
    <el-button
      v-for="g in groups"
      :key="g"
      class="grp-btn"
      :type="home.groupSelected === g ? 'primary' : 'default'"
      @click="home.setGroup(g)"
    >
      {{ g }}
    </el-button>
  </div>

  <el-skeleton v-if="home.loading" :rows="6" animated />

  <section v-else class="grid">
    <el-empty v-if="!list.length" description="当前分组暂无商品" />
    <ItemCard v-for="p in list" :key="p.id" :item="p" />
  </section>
</template>

<style scoped>
.hero {
  text-align: center;
  margin-bottom: 12px;
}

.hero h1 {
  margin: 0 0 6px;
  font-size: 28px;
  color: #111827;
  line-height: 1.65;
}

.hero p {
  margin: 0;
  color: #6b7280;
  line-height: 2.2;
}

.group-bar {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin: 16px 0 32px;
  flex-wrap: wrap;
}

.grid {
  display: grid;
  gap: 16px;
  grid-template-columns: repeat(2, 1fr);
}

@media (min-width: 768px) {
  .grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (min-width: 1024px) {
  .grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (min-width: 1280px) {
  .grid {
    grid-template-columns: repeat(5, 1fr);
  }
}

.grp-btn {
  border-radius: 15px;
  width: 65px;
  height: 40px;
  padding: 0 18px;
  font-size: 15px;
  font-weight: 500;
}
</style>
