<script setup lang="ts">
import {useRouter} from 'vue-router'
import {ShoppingCart} from '@element-plus/icons-vue'

const props = defineProps<{
  item: ItemCardVO
}>()

const router = useRouter()
const onClick = () => {
  router.push('/item/' + props.item.id)
}
</script>

<template>
  <el-card class="card">
    <div class="cover-box">
      <el-image :src="props.item.cover" fit="cover" class="cover"/>
    </div>
    <div class="card-body">
      <div class="title">{{ props.item.name }}</div>
      <div class="group-chip">
        <el-tag size="small" type="info" effect="plain">{{ props.item.group }}</el-tag>
      </div>
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
  border-radius: 4px;
  border: 1px solid #d4d7dd;
  padding: 0;
  background: #ffffff;
  box-shadow: none;
  transition: border-color .15s ease, background-color .15s ease;
}

.card:hover {
  border-color: #bfc3c9;
  background: #f9fafb;
}

.card :deep(.el-card__body) {
  padding: 0;
}

.cover-box {
  width: 100%;
  aspect-ratio: 1/1;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
  overflow: hidden;
  background: #f5f5f5;
}

.cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-body {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 14px 14px 16px;
}

.title {
  font-weight: 600;
  color: #111827;
  font-size: 18px;
  line-height: 1.35;
  min-height: 42px;
}

.group-chip {
  display: flex;
}

.meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.price {
  color: #ff4d4f;
  font-weight: 800;
  font-size: 20px;
}

.order-btn {
  padding: 4px 24px;
  line-height: 20px;
  font-size: 14px;
  border-radius: 6px;
  letter-spacing: .5px;
  font-weight: 600;
}
</style>

