<script setup>
import {ref} from 'vue'

// 示例数据：先不对接后端
const loading = ref(false)
const detail = ref({
  id: 1,
  name: '示例商品：AI 写真套餐',
  group: '1',
  cover: 'https://via.placeholder.com/480x320.png?text=Item+Cover',
  price: 19.9,
  salesCount: 128,
  stock: 50,
  qty: 1,
  // 支付方式示例字段
  payType: 'wechat',
  description: '示例商品介绍：这是一个用于演示的商品详情页面。',
  descHtml: '',
  images: [
    'https://via.placeholder.com/900x400.png?text=Detail+Image+1',
    'https://via.placeholder.com/900x400.png?text=Detail+Image+2'
  ]
})
</script>

<template>
  <div class="page">
    <div class="wrap" v-loading="loading">
      <el-card class="panel main-panel">
        <template #header>
          <div class="panel-header">
            <div class="panel-title">
              <span class="tag">商品详情</span>
            </div>
          </div>
        </template>

        <div class="main-body">
          <div class="cover-col">
            <el-image
                :src="detail.cover"
                fit="cover"
                class="cover-img"
                :preview-src-list="[detail.cover]"
            />
          </div>

          <div class="info-col">
            <div class="item-name">{{ detail.name }}</div>

            <div class="price-row">
              <div class="price">¥ {{ Number(detail.price).toFixed(2) }}</div>
              <div class="meta">
                <span class="sold">已售 {{ detail.salesCount }}</span>
                <span class="stock">库存 {{ detail.stock }}</span>
              </div>
            </div>

            <div class="form-row">
              <div class="label">购买数量</div>
              <el-input-number v-model="detail.qty" :min="1" :max="detail.stock || 999" />
            </div>

            <div class="form-row">
              <div class="label">支付方式</div>
              <el-radio-group v-model="detail.payType">
                <el-radio label="wechat">微信</el-radio>
                <el-radio label="alipay">支付宝</el-radio>
              </el-radio-group>
            </div>

            <div class="pay-row">
              <el-button class="order-btn" type="primary" size="large">下单</el-button>
            </div>
          </div>
        </div>
      </el-card>

      <el-card class="panel desc-panel">
        <template #header>
          <div class="panel-header">
            <h3 class="title small">商品详情介绍</h3>
          </div>
        </template>
        <div class="desc-body">
          <div v-if="detail.descHtml" class="rich-text" v-html="detail.descHtml" />
          <template v-else>
            <p class="desc-text">{{ detail.description }}</p>
            <div class="desc-images" v-if="detail.images && detail.images.length">
              <el-image
                  v-for="(img, i) in detail.images"
                  :key="i"
                  :src="img"
                  fit="contain"
                  class="desc-img"
                  :preview-src-list="detail.images"
              />
            </div>
          </template>
        </div>
      </el-card>

      <div class="pager-wrap">
        <el-pagination
            background
            layout="prev, pager, next"
            :total="1"
            :current-page="1"
            :page-size="1"
            disabled
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.page {
  max-width: 980px;
  margin: 32px auto 0; /* 顶栏与内容之间预留 32px 距离 */
}

.wrap {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.panel {
  background: #ffffff;
  border: 1px solid #ebeef5;
  border-radius: 8px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.panel-title {
  display: flex;
  align-items: center;
  gap: 8px;
}

.tag {
  font-size: 16px;
  font-weight: 600;
}

.title.small {
  font-size: 16px;
  margin: 0;
}

.main-body {
  display: flex;
  gap: 24px;
}

.cover-col {
  width: 320px;
  flex-shrink: 0;
}

.cover-img {
  width: 100%;
  border-radius: 8px;
}

.info-col {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.item-name {
  font-size: 20px;
  font-weight: 600;
}

.price-row {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.price {
  font-size: 26px;
  font-weight: 700;
  color: #ef4444;
}

.meta {
  display: flex;
  gap: 16px;
  font-size: 14px;
  color: #6b7280;
}

.sold,
.stock {
  font-size: 15px;
}

.stock {
  color: #10b981;
}

.form-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.label {
  width: 80px;
  color: #555;
}

.pay-row {
  margin-top: 4px;
}

.order-btn {
  font-size: 16px;
  padding: 8px 22px;
}

.desc-body {
  padding: 4px 0;
}

.desc-text {
  margin-bottom: 12px;
  color: #374151;
  line-height: 1.8;
}

.desc-images {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.desc-img {
  max-width: 100%;
  border-radius: 4px;
}

.rich-text :deep(img) {
  max-width: 100%;
}

.rich-text :deep(p) {
  line-height: 1.8;
}

.pager-wrap {
  margin: 24px 0 8px;
  display: flex;
  justify-content: center;
}

@media (max-width: 768px) {
  .main-body {
    flex-direction: column;
  }

  .cover-col {
    width: 100%;
  }
}
</style>