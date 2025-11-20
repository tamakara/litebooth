<script setup>
import {useItemStore} from '@/stores/itemStore'
import {computed, onMounted} from "vue";
import {useRouter} from "vue-router";

const item = useItemStore()
const router = useRouter()

const buyForm = computed(() => item.buyForm)
const itemInfo = computed(() => item.itemInfo)
const loading = computed(() => item.loading)

onMounted(() => {
  item.fetchItemInfo(router.currentRoute.value.params.id)
})
</script>

<template>
  <div class="page">
    <div class="wrap" v-loading="loading">
      <el-card class="panel main-panel">
        <div class="main-body">
          <div class="cover-col">
            <el-image
                :src="itemInfo.cover"
                fit="cover"
                class="cover-img"
                :preview-src-list="[itemInfo.cover]"
            />
          </div>

          <div class="info-col">
            <div class="item-name">{{ itemInfo.name }}</div>
            <div class="price-row">
              <div class="price">¥ {{ Number(itemInfo.price).toFixed(2) }}</div>
              <div class="stock-line">
                库存：{{ itemInfo.stock }}
              </div>
            </div>

            <div class="form-row">
              <div class="label">购买数量</div>
              <el-input-number v-model="buyForm.qty" :min="1" :max="itemInfo.stock"/>
            </div>

            <div class="form-row">
              <div class="label">支付方式</div>
              <el-radio-group v-model="buyForm.payType">
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
            <h3 class="title small">商品介绍</h3>
          </div>
        </template>
        <div class="desc-body">
          <div class="rich-text" v-html="itemInfo.description"/>
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
  margin: 32px auto 0;
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

.stock-line {
  font-size: 15px; /* 字号略大一些 */
  font-weight: 500;
  color: #10b981; /* 绿色强调库存 */
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