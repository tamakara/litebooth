<script setup>
import {onMounted, computed} from 'vue'
import {useProductStore} from '../stores/productStore'
import {useRouter} from 'vue-router'
import {ShoppingCart} from '@element-plus/icons-vue'

const router = useRouter()
const product = useProductStore()

onMounted(() => {
  if (!product.products.length) product.fetchProducts()
})

const list = computed(() => product.filteredProducts)
</script>

<template>
  <section class="hero">
    <h1>发现好物，享受生活</h1>
    <p>简洁优雅的小店页面示例，支持商家自定义分组浏览。</p>
  </section>

  <div class="group-bar">
    <el-button
        v-for="g in product.groups"
        :key="g"
        class="grp-btn"
        :type="product.groupSelected === g ? 'primary' : 'default'"
        @click="product.setGroup(g)"
    >{{ g }}
    </el-button>
  </div>

  <el-skeleton v-if="product.loading" :rows="6" animated/>

  <section v-else class="grid">
    <el-empty v-if="!list.length" description="当前分组暂无商品"/>
    <el-card v-for="p in list" :key="p.id" class="card">
      <div class="cover-box">
        <el-image :src="p.cover" fit="cover" class="cover"/>
      </div>
      <div class="card-body">
        <div class="title">{{ p.name }}</div>
        <div class="group-chip">
          <el-tag size="small" type="info" effect="plain">{{ p.group }}</el-tag>
        </div>
        <div class="meta">
          <span class="price">¥ {{ p.price }}</span>
          <el-button class="order-btn" type="primary" @click="router.push('/orders')">
            <el-icon style="margin-right:6px">
              <ShoppingCart/>
            </el-icon>
            下单
          </el-button>
        </div>
      </div>
    </el-card>
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
}

.hero p {
  margin: 0;
  color: #6b7280;
}

.group-bar {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin: 10px 0 18px;
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

.grp-btn {
  border-radius: 10px;
  padding: 8px 14px;
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
