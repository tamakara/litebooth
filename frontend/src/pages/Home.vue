<script setup>
import {onMounted, computed} from 'vue'
import {useHomeStore} from '../stores/homeStore'
import Announcement from '../components/Announcement.vue'
import ItemCard from '../components/ItemCard.vue'

const home = useHomeStore()

const homeInfo = computed(() => home.homeInfo)
const searchForm = computed(() => home.searchForm)
const itemCardList = computed(() => home.itemCardList)

const handlePageChange = (page) => {
  searchForm.value.pageNumber = page
  home.fetchItemInfo()
}

const handleSizeChange = (size) => {
  searchForm.value.pageSize = size
  searchForm.value.pageNumber = 1
  home.fetchItemInfo()
}

onMounted(() => {
  home.fetchHomeInfo()
  home.fetchItemInfo()
})
</script>

<template>
  <Announcement v-if="homeInfo.announcement">
    {{ homeInfo.announcement }}
  </Announcement>

  <section class="hero">
    <h1>{{ homeInfo.title }}</h1>
    <p>{{ homeInfo.subtitle }}</p>
  </section>

  <div class="group-bar">
    <el-button
        v-for="g in homeInfo.groups"
        :key="g"
        class="grp-btn"
        :type="searchForm.group === g ? 'primary' : 'default'"
        @click="searchForm.group=g"
    >
      {{ g }}
    </el-button>
  </div>

  <section class="item-grid">
    <ItemCard
        v-for="it in itemCardList.items"
        :key="it.id"
        :item="it"
    />
  </section>

  <div class="pager-wrap">
    <el-pagination
        background
        layout="prev, pager, next"
        :total="itemCardList.total"
        :current-page="searchForm.pageNumber"
        :page-size="searchForm.pageSize"
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
    />
  </div>
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

.item-grid {
  display: grid;
  gap: 16px;
  grid-template-columns: repeat(2, 1fr);
}

@media (min-width: 768px) {
  .item-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (min-width: 1024px) {
  .item-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (min-width: 1280px) {
  .item-grid {
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

.pager-wrap {
  margin: 24px 0 8px;
  display: flex;
  justify-content: center;
}
</style>
