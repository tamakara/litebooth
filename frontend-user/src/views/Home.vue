<script setup lang="ts">
import {onMounted, computed, toRefs} from 'vue'
import {useHomeStore} from '@/stores/homeStore'
import ItemCard from '@/components/ItemCard.vue'

const home = useHomeStore()

const {homeInfo, itemCardQueryForm: queryForm, itemCardPage} = toRefs(home)
const itemCardList = computed(() => itemCardPage.value.itemCardList)
const total = computed(() => itemCardPage.value.total)

const updateQuery = async (payload: object) => {
  Object.assign(queryForm.value, payload)
  await home.fetchItemCardPageVO()
}

const handleGroupChange = (group: string) => updateQuery({group: group, pageNum: 1})
const handlePageChange = (page: number) => updateQuery({pageNum: page})
const handleSizeChange = (size: number) => updateQuery({pageSize: size, pageNum: 1})

onMounted(async () => {
  await home.fetchShopInfoVO()
  await home.fetchItemCardPageVO()
})
</script>

<template>
  <section class="announcement" v-if="homeInfo.announcement">
    <h4 class="anno-title">公告</h4>
    <div class="anno-text">
      {{ homeInfo.announcement }}
    </div>
  </section>

  <section class="hero">
    <h1>{{ homeInfo.title }}</h1>
    <p>{{ homeInfo.subtitle }}</p>
  </section>

  <section class="group-bar">
    <el-button
        v-for="g in homeInfo.groups"
        :key="g"
        class="grp-btn"
        :type="queryForm.group === g ? 'primary' : 'default'"
        @click="handleGroupChange(g)"
    >
      {{ g }}
    </el-button>
  </section>

  <section class="item-grid">
    <ItemCard
        v-for="i in itemCardList"
        :key="i.id"
        :item="i"
    />
  </section>

  <div class="pager-wrap">
    <el-pagination
        background
        layout="sizes, prev, pager, next"
        :total="total"
        :current-page="queryForm.pageNum"
        :page-size="queryForm.pageSize"
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
    />
  </div>
</template>

<style scoped>
.announcement {
  background: #fff;
  border: 2px solid #e5e7eb;
  border-radius: 8px;
  padding: 20px 24px;
  margin: 16px 0;
  width: 100%;
  box-sizing: border-box;
}

.anno-title {
  margin-bottom: 8px;
  font-size: 20px;
  font-weight: 800;
  color: #111827;
}

.anno-text {
  font-size: 16px;
  line-height: 1.75;
  color: #343a40;
}

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
