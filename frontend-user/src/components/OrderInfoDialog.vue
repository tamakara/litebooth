<script setup lang="ts">
import {computed, ref, UnwrapRef} from 'vue'
import {formatDate} from '@/utils/index.js'
import {OrderInfoVO} from "@/types";
import {OrderStatus, OrderStatusText, PaymentMethodText} from "@/types/enums";

const props = defineProps<{
  visible: boolean
  orderInfo: UnwrapRef<OrderInfoVO>
}>()

const emit = defineEmits(['update:visible', 'pay', 'view-content'])

const dialogVisible = computed({
  get: () => props.visible,
  set: (value) => emit('update:visible', value)
})

const orderCreatedAtFormatted = computed(() => formatDate(props.orderInfo.createdAt))
const orderPaymentAtFormatted = computed(() => formatDate(props.orderInfo.paymentAt))

const isUnpaid = computed(() => props.orderInfo.status === OrderStatus.UNPAID)
const isExpired = computed(() => props.orderInfo.status === OrderStatus.EXPIRED)
const isFinished = computed(() => props.orderInfo.status === OrderStatus.FINISHED)

const handlePay = () => {
  emit('pay')
}

const handleClose = () => {
  emit('update:visible', false)
}

const handleViewContent = () => {
  emit('view-content')
}
</script>

<template>
  <el-dialog v-model="dialogVisible" title="订单信息" width="560px">
    <div class="order-info-card">
      <div class="order-info">
        <div class="order-row">
          <span class="label">订单号</span>
          <span class="value">{{ orderInfo.id }}</span>
        </div>
        <div class="order-row">
          <span class="label">状态</span>
          <span class="value">{{ OrderStatusText[orderInfo.status] }}</span>
        </div>
        <div class="order-row">
          <span class="label">商品名称</span>
          <span class="value">{{ orderInfo.itemName }}</span>
        </div>
        <div class="order-row">
          <span class="label">单价</span>
          <span class="value">¥ {{ Number(orderInfo.itemPrice).toFixed(2) }}</span>
        </div>
        <div class="order-row">
          <span class="label">数量</span>
          <span class="value">{{ orderInfo.quantity }}</span>
        </div>
        <div class="order-row">
          <span class="label">总价</span>
          <span class="value">¥ {{ Number(orderInfo.amount).toFixed(2) }}</span>
        </div>
        <div class="order-row">
          <span class="label">支付方式</span>
          <span class="value">{{ PaymentMethodText[orderInfo.paymentMethod] }}</span>
        </div>
        <div class="order-row">
          <span class="label">创建时间</span>
          <span class="value">{{ orderCreatedAtFormatted }}</span>
        </div>
        <div class="order-row">
          <span class="label">付款时间</span>
          <span class="value">{{ orderPaymentAtFormatted }}</span>
        </div>
      </div>
    </div>

    <template #footer>
      <span class="dialog-footer">
        <el-button
            v-if="isUnpaid"
            type="primary"
            @click="handlePay"
        >
          付款
        </el-button>
        <el-button
            v-else-if=" isFinished"
            type="primary"
            plain
            @click="handleViewContent"
        >
          查看卡密
        </el-button>
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.order-info-card {
  margin: 0 10px;
  padding: 16px 20px 14px;
  border-radius: 4px;
  border: 1px solid #e0e3ec;
  background: linear-gradient(135deg, #f1f3f8 0%, #e6e9f2 100%);
}


.order-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 6px 0;
  font-size: 15px;
}

.order-row {
  display: flex;
  align-items: flex-start;
  font-size: 15px;
  line-height: 1.6;
  border-bottom: 1px dashed rgba(0, 0, 0, 0.06);
  padding-bottom: 6px;
}

.order-row:last-child {
  border-bottom: none;
}

.order-row .label {
  flex: 0 0 90px;
  color: #555;
  font-weight: 500;
}

.order-row .value {
  flex: 1;
  text-align: left;
  color: #222;
}


.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

:deep(.el-dialog__title) {
  font-size: 20px;
  font-weight: 600;
}

:deep(.el-dialog__body) {
  padding-top: 10px;
  padding-bottom: 22px;
  font-size: 15px;
}
</style>
