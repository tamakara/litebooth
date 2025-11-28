<script setup>
import {computed, onMounted, ref, toRefs} from 'vue'
import {orderStore} from '@/stores/orderStore.ts'
import {formatDate} from "@/utils/index.js";

const order = orderStore()

const {orderInfoPage, queryForm} = toRefs(order)

const orderInfoList = computed(() => orderInfoPage.value.orderInfoList)
const total = computed(() => orderInfoPage.value.total)

const contentListDialogVisible = ref(false)
const currentContentList = ref([])

const openContentListDialog = (contentList) => {
  currentContentList.value = contentList
  contentListDialogVisible.value = true
}

const handlePageChange = async () => {
  await order.fetchOrderInfoPageVO()
}

const statusType = (s) => s && s.includes('待') ? 'warning' : (s && s.includes('完成') ? 'success' : 'info')

onMounted(async () => {
  await order.fetchOrderInfoPageVO()
})
</script>

<template>

  <el-card class="panel">
    <template #header>历史订单</template>
    <el-empty v-if="! orderInfoList.length" description="暂无订单"/>
    <div v-else class="order-list">
      <div v-for="o in orderInfoList" :key="o.id" class="order-item">
        <div class="order-head">
          <div class="left">
            <div class="ono">订单号：<span class="mono">{{ o.id }}</span></div>
            <div class="date">创建时间：{{ formatDate(o.createdAt) }}</div>
            <div class="date">付款时间：{{ formatDate(o.createdAt) }}</div>
            <div class="field">商品名称：{{ o.itemName }}</div>
            <div class="field">数量：{{ o.quantity }}</div>
            <div class="field">支付方式：{{ o.payMethod }}</div>
          </div>
          <div class="right">
            <el-tag :type="statusType(o.status)">{{ o.status }}</el-tag>
            <div class="amount">合计：¥ {{ Number(o.amount || 0).toFixed(2) }}</div>
            <el-button type="primary" size="small" @click="openContentListDialog(o.contentList)">显示卡密
            </el-button>
          </div>
        </div>
      </div>
    </div>
    <!-- 分页组件 -->
    <div v-if="total > 0" class="pagination-wrapper">
      <el-pagination
          layout="prev, pager, next"
          :current-page="queryForm.pageNum"
          :page-size="queryForm.pageSize"
          :total="total"
          @current-change="handlePageChange"
      />
    </div>
  </el-card>

  <!-- 卡密弹窗 -->
  <el-dialog v-model="contentListDialogVisible" title="卡密内容" width="500px">
    <pre v-for="content in currentContentList" class="card-content">{{ content }}</pre>
    <template #footer>
      <el-button type="primary" @click="contentListDialogVisible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>

.panel {
  background: #ffffff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

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

.pagination-wrapper {
  margin-top: 12px;
  display: flex;
  justify-content: center;
}

.card-content {
  max-height: 320px;
  overflow: auto;
  background: #f9fafb;
  padding: 12px;
  border-radius: 4px;
  white-space: pre-wrap;
  word-break: break-all;
}

.field {
  font-size: 13px;
  color: #4b5563;
}
</style>
