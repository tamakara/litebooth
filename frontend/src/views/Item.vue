<script setup>
import {useItemStore} from '@/stores/itemStore.js'
import {onMounted, ref, toRefs} from 'vue'
import {useRouter} from 'vue-router'
import OrderInfoDialog from '@/components/OrderInfoDialog.vue'

const item = useItemStore()
const router = useRouter()

const {itemInfo, orderQueryForm: queryForm, orderInfo, loading} = toRefs(item)

const orderDialogVisible = ref(false)

const onBuyClick = async () => {
  await item.createOrder()
  orderDialogVisible.value = true
}

const onCancelOrderClick = async () => {
  await item.cancelOrder()
  orderDialogVisible.value = false
}

const onPayOrderClick = async () => {
  await item.payOrder()
  orderDialogVisible.value = false
  await router.push({name: 'user'})
}

onMounted(async () => {
  await item.fetchItemInfoVO(router.currentRoute.value.params.id)
})
</script>

<template>
  <div class="page" v-loading="loading">
    <!-- 商品面板 -->
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
              <span v-if="itemInfo.stock > 0">库存: {{ itemInfo.stock }}</span>
              <span v-else style="color: red; font-weight: bold;">商品缺货</span>
            </div>
          </div>

          <div class="form-row">
            <div class="label">购买数量</div>
            <el-input-number
                v-if="itemInfo.stock > 0"
                v-model="queryForm.quantity"
                :min="1"
                :max="itemInfo.stock"
            />
            <el-input-number
                v-else
                v-model="queryForm.quantity"
                :min="0"
                :max="0"
            />
          </div>

          <div class="form-row">
            <div class="label">支付方式</div>
            <el-radio-group v-model="queryForm.payMethod" :disabled="itemInfo.stock === 0">
              <el-radio label="wxpay">微信</el-radio>
              <el-radio label="alipay">支付宝</el-radio>
            </el-radio-group>
          </div>

          <div class="pay-row">
            <el-button
                class="order-btn"
                type="primary"
                size="large"
                :disabled="itemInfo.stock === 0"
                @click="onBuyClick"
            >
              下单
            </el-button>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 商品介绍 -->
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

    <!-- 订单弹窗 -->
    <OrderInfoDialog
        v-model:visible="orderDialogVisible"
        :order-info="orderInfo"
        @cancel="onCancelOrderClick"
        @pay="onPayOrderClick"
    />
  </div>
</template>

<style scoped>
.page {
  max-width: 980px;
  margin: 32px auto 0;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.panel {
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 8px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  font-size: 26px;
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
  font-size: 15px;
  font-weight: 500;
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

/* 商品描述 */
.desc-body {
  padding: 4px 0;
}

.rich-text :deep(img) {
  max-width: 100%;
}

.rich-text :deep(p) {
  line-height: 1.8;
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
