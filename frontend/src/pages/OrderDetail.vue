<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useOrderStore } from '../stores/orderStore'

const route = useRoute()
const router = useRouter()
const store = useOrderStore()

const order = computed(() => store.byId(route.params.id))
const fmt = (d) => new Date(d).toLocaleString()
const total = computed(() => order.value?.items.reduce((s, i) => s + i.price * i.qty, 0) || 0)
</script>

<template>
  <div class="wrap" v-if="order">
    <div class="head">
      <h2>订单详情</h2>
      <div class="actions">
        <el-button @click="router.push('/orders')">返回列表</el-button>
        <el-button type="primary" @click="router.push('/')">去首页</el-button>
      </div>
    </div>

    <el-card class="glass">
      <div class="info">
        <div>
          <div class="label">订单号</div>
          <div class="value mono">{{ order.number }}</div>
        </div>
        <div>
          <div class="label">状态</div>
          <el-tag :type="order.status.includes('待') ? 'warning' : (order.status.includes('完成') ? 'success' : 'info')">{{ order.status }}</el-tag>
        </div>
        <div>
          <div class="label">创建时间</div>
          <div class="value">{{ fmt(order.createdAt) }}</div>
        </div>
        <div>
          <div class="label">买家</div>
          <div class="value">{{ order.user.name }} · {{ order.user.email }} · {{ order.user.phone }}</div>
        </div>
      </div>
    </el-card>

    <el-card class="glass">
      <template #header>商品清单</template>
      <el-table :data="order.items" stripe>
        <el-table-column label="商品" min-width="220">
          <template #default="{ row }">
            <div class="item">
              <el-image :src="row.cover" fit="cover" class="thumb" />
              <div>
                <div class="name">{{ row.name }}</div>
                <div class="sku">{{ row.sku }}</div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="单价" width="120">
          <template #default="{ row }">¥ {{ row.price.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="qty" label="数量" width="100" />
        <el-table-column label="小计" width="120">
          <template #default="{ row }">¥ {{ (row.price * row.qty).toFixed(2) }}</template>
        </el-table-column>
      </el-table>
      <div class="total">合计：<span>¥ {{ total.toFixed(2) }}</span></div>
    </el-card>

  </div>
  <el-empty v-else description="未找到该订单" />
</template>

<style scoped>
.wrap { display:flex; flex-direction: column; gap: 12px; }
.head { display:flex; align-items:center; justify-content: space-between; }
.actions { display:flex; gap: 8px; }
.info { display:grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 12px; }
.label { color:#94a3b8; font-size: 12px; margin-bottom: 4px; }
.value { font-weight: 600; }
.mono { font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace; }
.item { display:flex; align-items:center; gap: 12px; }
.thumb { width: 72px; height: 48px; border-radius: 8px; }
.name { font-weight: 600; }
.sku { color:#94a3b8; font-size: 12px; }
.total { text-align: right; margin-top: 12px; font-weight: 700; }
.glass { background: rgba(255,255,255,.06); backdrop-filter: blur(8px); border: 1px solid rgba(255,255,255,.12); }
</style>

