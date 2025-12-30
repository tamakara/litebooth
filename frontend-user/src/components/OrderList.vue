<script setup lang="ts">
import {PropType} from 'vue'
import {OrderInfoVO} from "@/types";
import {OrderStatus, OrderStatusText, PaymentMethodText} from "@/types/enums/order";
import {formatDate} from "@/utils";

defineProps({
  orderList: {
    type: Array as PropType<OrderInfoVO[]>,
    required: true
  }
})

const emit = defineEmits(['pay', 'viewContent'])

const statusType = (s?: string) => s && s.includes('待') ? 'warning' : (s && s.includes('完成') ? 'success' : 'info')

const handlePay = (orderId: string) => {
  emit('pay', orderId)
}

const openContentListDialog = (contentList?: string[]) => {
  emit('viewContent', contentList)
}
</script>

<template>
  <el-empty v-if="!orderList.length" description="暂无订单"/>
  <div v-else class="order-list">
    <div v-for="o in orderList" :key="o.id" class="order-item">
      <div class="order-head">
        <div class="left">
          <div class="ono">订单号：<span class="mono">{{ o.id }}</span></div>
          <div class="date">创建时间：{{ formatDate(o.createdAt) }}</div>
          <div class="date">付款时间：{{ formatDate(o.paymentAt || null) }}</div>
          <div class="field">商品名称：{{ o.itemName }}</div>
          <div class="field">数量：{{ o.quantity }}</div>
          <div class="field">支付方式：{{ PaymentMethodText[o.paymentMethod] }}</div>
        </div>
        <div class="right">
          <el-tag :type="statusType(o.status)">{{ OrderStatusText[o.status] }}</el-tag>
          <div class="amount">合计：¥ {{ Number(o.amount || 0).toFixed(2) }}</div>
          <el-button
              v-if="o.status===OrderStatus.UNPAID"
              type="primary"
              size="small"
              @click="handlePay(o.id)"
          >
            付款
          </el-button>
          <el-button
              v-else-if="o.status===OrderStatus.FINISHED"
              type="primary"
              size="small"
              @click="openContentListDialog(o.contentList as string[])"
          >
            查看卡密
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.order-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.order-item {
  border: 1px dashed #e5e7eb;
  border-radius: 8px;
  padding: 10px;
}

.order-head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
}

.order-head .left {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.ono {
  font-weight: 600;
}

.mono {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace;
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
}

.amount {
  font-weight: 700;
}

.field {
  font-size: 13px;
  color: #4b5563;
}
</style>

