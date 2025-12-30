<script setup lang="ts">
import {useRouter} from 'vue-router'
import {ShoppingCart} from '@element-plus/icons-vue'
import {ItemCardVO} from "@/types";

const props = defineProps<{
  item: ItemCardVO
}>()

const router = useRouter()
const onClick = () => {
  router.push('/item/' + props.item.id)
}
</script>

<template>
  <el-card class="card" @click="onClick">
    <div class="cover-box">
      <el-image :src="props.item.coverFileUrl" fit="cover" class="cover"/>
    </div>
    <div class="card-body">
      <div class="title">{{ props.item.name }}</div>
      <div class="meta">
        <span class="price">¥ {{ props.item.price }}</span>
        <el-button class="order-btn" type="primary" @click="onClick">
          <el-icon style="margin-right:6px">
            <ShoppingCart/>
          </el-icon>
          下单
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<style scoped>
.card {
  overflow: hidden;
  border-radius: var(--border-radius-base);
  border: 1px solid #e5e7eb;
  padding: 0;
  background: #ffffff;
  box-shadow: var(--shadow-sm);
  transition: all 0.3s ease;
  cursor: pointer;
}

.card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
  border-color: var(--color-primary);
}

.card :deep(.el-card__body) {
  padding: 0;
}

.cover-box {
  width: 100%;
  aspect-ratio: 1/1;
  overflow: hidden;
  background: #f3f4f6;
  position: relative;
}

.cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.card:hover .cover {
  transform: scale(1.05);
}

.card-body {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 16px;
}

.title {
  font-weight: 600;
  color: var(--color-text-main);
  font-size: 16px;
  line-height: 1.5;
  height: 48px; /* Fixed height for 2 lines */
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: auto;
}

.price {
  color: #ef4444;
  font-weight: 700;
  font-size: 18px;
}

.order-btn {
  padding: 8px 16px;
  font-weight: 500;
  border-radius: 20px;
}
</style>

