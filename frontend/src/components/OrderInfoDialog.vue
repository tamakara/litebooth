<script setup lang="ts">
import {computed} from 'vue'
import {formatDate} from "@/utils/index.js";

const props = defineProps<{
  visible: boolean
  orderInfo: UnwrapRef<OrderInfoVO>
}>()

const emit = defineEmits(['update:visible', 'pay'])

const dialogVisible = computed({
  get: () => props.visible,
  set: (value) => emit('update:visible', value)
})

const orderCreatedAtFormatted = computed(() => formatDate(props.orderInfo.createdAt))

const handlePay = () => {
  emit('pay')
}
</script>

<template>
  <el-dialog v-model="dialogVisible" title="订单信息" width="560px">
    <div class="order-info">
      <div class="order-row"><span class="label">订单号</span><span>{{ orderInfo.id }}</span></div>
      <div class="order-row"><span class="label">状态</span><span>{{ orderInfo.status }}</span></div>
      <div class="order-row"><span class="label">商品名称</span><span>{{ orderInfo.itemName }}</span></div>
      <div class="order-row"><span class="label">单价</span><span>¥ {{
          Number(orderInfo.itemPrice).toFixed(2)
        }}</span></div>
      <div class="order-row"><span class="label">数量</span><span>{{ orderInfo.quantity }}</span></div>
      <div class="order-row"><span class="label">总价</span><span
          class="price">¥ {{ Number(orderInfo.amount).toFixed(2) }}</span></div>
      <div class="order-row"><span
          class="label">支付方式</span><span>{{ orderInfo.payMethod === 'wxpay' ? '微信' : '支付宝' }}</span></div>
      <div class="order-row"><span class="label">创建时间</span><span>{{ orderCreatedAtFormatted }}</span></div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handlePay">去付款</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
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
  color: #ef4444;
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
</style>
