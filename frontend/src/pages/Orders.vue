<script setup>
import {computed} from 'vue'
import {useRouter} from 'vue-router'
import {useOrderStore} from '../stores/orderStore'

const router = useRouter()
const store = useOrderStore()

const data = computed(() => store.filtered)
const toDetail = (row) => router.push(`/order/${row.id}`)
const fmt = (d) => new Date(d).toLocaleString()
const triggerSearch = () => {
}
</script>

<template>
  <div class="page">
    <div class="head">
      <h2>订单查询</h2>
      <div class="tools">
        <el-input v-model="store.keyword" placeholder="输入订单号/邮箱/手机号搜索" clearable class="kw"/>
        <el-button type="primary" @click="triggerSearch">搜索</el-button>
      </div>
    </div>

    <el-card class="glass">
      <el-table :data="data" stripe>
        <el-table-column prop="number" label="订单号" min-width="160"/>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status.includes('待') ? 'warning' : (row.status.includes('完成') ? 'success' : 'info')">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="amount" label="金额" width="120">
          <template #default="{ row }">¥ {{ row.amount.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" min-width="180">
          <template #default="{ row }">{{ fmt(row.createdAt) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="toDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<style scoped>
.page {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.head {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.tools {
  display: flex;
  gap: 8px;
  align-items: center;
}

.kw {
  width: 320px;
}

.glass {
  background: rgba(255, 255, 255, .06);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(255, 255, 255, .12);
}
</style>
