<script setup>
import {useItemStore} from '@/stores/itemStore'
import {computed, onMounted, ref} from 'vue'
import {useRouter} from 'vue-router'
import dayjs from 'dayjs'

const item = useItemStore()
const router = useRouter()

const orderForm = computed(() => item.orderForm)
const itemInfo = computed(() => item.itemInfo)
const loading = computed(() => item.loading)
const orderInfo = computed(() => item.orderInfo)

const orderCreatedAtFormatted = computed(() => {
  const v = orderInfo.value?.createdAt
  if (!v) return ''
  return dayjs(v).format('YYYY年MM月DD日 HH:mm:ss')
})

const orderDialogVisible = ref(false)

const onBuyClick = async () => {
  await item.createOrder()
  orderDialogVisible.value = true
}

const onCancelOrderClick = async () => {
  await item.cancelOrder()
  orderDialogVisible.value = false
}

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
              <div class="price">
                ¥ {{ Number(itemInfo.price).toFixed(2) }}
              </div>
              <div class="stock-line">
                库存: {{ itemInfo.stock }}
              </div>
            </div>

            <div class="form-row">
              <div class="label">购买数量</div>
              <el-input-number v-model="orderForm.quantity" :min="1" :max="itemInfo.stock"/>
            </div>

            <div class="form-row">
              <div class="label">支付方式</div>
              <el-radio-group v-model="orderForm.payMethod">
                <el-radio label="wechat">微信</el-radio>
                <el-radio label="alipay">支付宝</el-radio>
              </el-radio-group>
            </div>

            <div class="pay-row">
              <el-button class="order-btn" type="primary" size="large" @click="onBuyClick">下单</el-button>
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

      <el-dialog
          v-model="orderDialogVisible"
          title="订单信息"
          width="560px"
      >
        <div class="order-info">
          <div class="order-row"><span class="label">订单号</span><span>{{ orderInfo.id }}</span></div>
          <div class="order-row"><span class="label">状态</span><span>{{ orderInfo.status }}</span></div>
          <div class="order-row"><span class="label">商品名称</span><span>{{ orderInfo.itemName }}</span></div>
          <div class="order-row"><span class="label">单价</span><span>¥ {{
              Number(orderInfo.itemPrice).toFixed(2)
            }}</span></div>
          <div class="order-row"><span class="label">数量</span><span>{{ orderInfo.quantity }}</span></div>
          <div class="order-row"><span class="label">总价</span><span
              class="price">¥ {{ Number(orderInfo.totalPrice).toFixed(2) }}</span></div>
          <div class="order-row"><span
              class="label">支付方式</span><span>{{ orderInfo.payMethod === 'wechat' ? '微信' : '支付宝' }}</span></div>
          <div class="order-row"><span class="label">创建时间</span><span>{{ orderCreatedAtFormatted }}</span></div>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="danger" @click="onCancelOrderClick">取消订单</el-button>
            <el-button type="primary">去付款</el-button>
          </span>
        </template>
      </el-dialog>
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

.desc-body {
  padding: 4px 0;
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

.order-info {
  display: flex;
  flex-direction: column;
  gap: 14px;
  padding: 4px 2px;
  font-size: 16px;
}

.order-row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  font-size: 16px;
  line-height: 1.7;
  border-bottom: 1px dashed #eee;
  padding-bottom: 4px;
}

.order-row:last-child {
  border-bottom: none;
}

.order-row .label {
  flex: 0 0 90px;
  color: #666;
  font-weight: 500;
}

.order-row span:last-child {
  flex: 1;
  text-align: right;
  color: #333;
}

.order-row .price {
  font-weight: 700;
  color: #ef4444 !important;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

:deep(.el-dialog__title) {
  font-size: 18px;
  font-weight: 600;
}

:deep(.el-dialog__body) {
  padding-top: 8px;
  padding-bottom: 18px;
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