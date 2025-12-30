<script setup lang="ts">
import {onMounted, computed, toRefs, ref} from 'vue'
import {useHomeStore} from '@/store/homeStore'
import ItemCard from '@/components/ItemCard.vue'
import HomeAnnouncement from '@/components/HomeAnnouncement.vue'
import HomeHero from '@/components/HomeHero.vue'

const home = useHomeStore()

const {shopInfo: homeInfo, groupList: groups, itemCardQueryForm: queryForm, itemCardPage} = toRefs(home)
const itemCardList = computed(() => itemCardPage.value.records)
const total = computed(() => itemCardPage.value.total)
const loading = ref(false)

const updateQuery = async (payload: object) => {
  loading.value = true
  try {
    Object.assign(queryForm.value, payload)
    await home.fetchItemCardPageVO()
  } finally {
    loading.value = false
  }
}

const handleGroupChange = (groupId: number) => updateQuery({groupId: groupId, pageNum: 1})
const handlePageChange = (page: number) => updateQuery({pageNum: page})
const handleSizeChange = (size: number) => updateQuery({pageSize: size, pageNum: 1})

onMounted(async () => {
  loading.value = true
  try {
    await home.fetchGroupListVO()
    await home.fetchItemCardPageVO()
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <HomeAnnouncement :announcement="homeInfo.homeAnnouncement"/>

  <HomeHero
      :title="homeInfo.homeTitle"
      :subtitle="homeInfo.homeSubtitle"
  />

  <section class="group-bar">
    <el-button
        v-for="g in groups"
        :key="g.id"
        class="grp-btn"
        :type="queryForm.groupId === g.id ? 'primary' : 'default'"
        @click="handleGroupChange(g.id)"
        round
    >
      {{ g.name }}
    </el-button>
  </section>

  <section class="item-grid" v-if="loading">
    <el-card v-for="n in 8" :key="n" class="card-skeleton">
      <el-skeleton animated>
        <template #template>
          <el-skeleton-item variant="image" style="width: 100%; aspect-ratio: 1/1" />
          <div style="padding: 14px">
            <el-skeleton-item variant="h3" style="width: 50%" />
            <div style="display: flex; justify-content: space-between; margin-top: 16px; height: 32px; align-items: center">
              <el-skeleton-item variant="text" style="width: 30%" />
              <el-skeleton-item variant="button" style="width: 30%; border-radius: 20px" />
            </div>
          </div>
        </template>
      </el-skeleton>
    </el-card>
  </section>

  <section class="item-grid" v-else>
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

.card-skeleton {
  border-radius: 12px;
  overflow: hidden;
}
</style>
