<script setup>
import {reactive, watchEffect, computed, ref} from 'vue'
import {useUserStore} from '../stores/userStore'
import {useOrderStore} from '../stores/orderStore'
import {ElMessage} from 'element-plus'

const user = useUserStore()
const orders = useOrderStore()

const edit = reactive({name: '', email: '', phone: ''})
const editing = ref(false)
const fileInput = ref(null)

watchEffect(() => {
  if (user.profile && !editing.value) {
    edit.name = user.profile.name || ''
    edit.email = user.profile.email || ''
    edit.phone = user.profile.phone || ''
  }
})

const startEdit = () => {
  editing.value = true
}
const cancelEdit = () => {
  editing.value = false
  if (user.profile) {
    edit.name = user.profile.name
    edit.email = user.profile.email
    edit.phone = user.profile.phone
  }
}
const save = () => {
  if (!user.isLogin) return
  user.updateProfile({name: edit.name, email: edit.email, phone: edit.phone})
  ElMessage.success('资料已保存')
  editing.value = false
}

const triggerAvatar = () => {
  fileInput.value?.click()
}
const onFileChange = (e) => {
  const f = e.target.files?.[0]
  if (!f) return
  const reader = new FileReader()
  reader.onload = () => {
    user.updateProfile({avatar: reader.result})
    ElMessage.success('头像已更新')
    e.target.value = ''
  }
  reader.readAsDataURL(f)
}

const myOrders = computed(() => {
  if (!user.isLogin) return []
  const email = user.profile?.email?.toLowerCase() || ''
  return orders.orders.filter(o => o.user.email.toLowerCase() === email)
})
const fmt = (d) => new Date(d).toLocaleString()
const statusType = (s) => s.includes('待') ? 'warning' : (s.includes('完成') ? 'success' : 'info')
</script>

<template>
  <div class="wrap">
    <h2>用户信息</h2>
    <template v-if="user.isLogin">
      <el-card class="panel">
        <template #header>基本资料</template>
        <div class="profile">
          <div class="avatar-col">
            <el-avatar :size="80" :src="user.profile?.avatar" class="avatar-preview" @click="triggerAvatar">
              {{ user.username.slice(0, 1).toUpperCase() }}
            </el-avatar>
            <div class="avatar-hint">点击更换头像</div>
            <input ref="fileInput" type="file" accept="image/*" class="hidden-file" @change="onFileChange"/>
          </div>
          <div class="fields">
            <el-form class="user-form" label-width="96px" :disabled="!editing">
              <el-form-item label="用户名">
                <el-input v-model="edit.name" placeholder="输入用户名"/>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="edit.email" placeholder="输入邮箱"/>
              </el-form-item>
              <el-form-item label="手机号">
                <el-input v-model="edit.phone" placeholder="输入手机号"/>
              </el-form-item>
              <el-form-item v-if="editing" class="form-actions-inline">
                <div class="form-action-group">
                  <el-button type="primary" @click="save">保存</el-button>
                  <el-button @click="cancelEdit">取消</el-button>
                  <el-button type="danger" @click="user.logout()">退出登录</el-button>
                </div>
              </el-form-item>
            </el-form>
            <div v-if="!editing" class="actions-row">
              <el-button type="primary" @click="startEdit">编辑资料</el-button>
              <el-button type="danger" @click="user.logout()">退出登录</el-button>
            </div>
          </div>
        </div>
      </el-card>

      <el-card class="panel">
        <template #header>历史订单</template>
        <el-empty v-if="!myOrders.length" description="暂无订单"/>
        <div v-else class="order-list">
          <div v-for="o in myOrders" :key="o.id" class="order-item">
            <div class="order-head">
              <div class="left">
                <div class="ono">订单号：<span class="mono">{{ o.number }}</span></div>
                <div class="date">付款日期：{{ fmt(o.createdAt) }}</div>
              </div>
              <div class="right">
                <el-tag :type="statusType(o.status)">{{ o.status }}</el-tag>
                <div class="amount">合计：¥ {{ o.amount.toFixed(2) }}</div>
              </div>
            </div>
            <el-table :data="o.items" size="small" class="items" :border="false" :stripe="true">
              <el-table-column label="商品" min-width="220">
                <template #default="{ row }">
                  <div class="item">
                    <el-image :src="row.cover" fit="cover" class="thumb"/>
                    <div>
                      <div class="name">{{ row.name }}</div>
                      <div class="sku mono">{{ row.sku }}</div>
                    </div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="单价" width="120">
                <template #default="{ row }">¥ {{ row.price.toFixed(2) }}</template>
              </el-table-column>
              <el-table-column prop="qty" label="数量" width="100"/>
              <el-table-column label="小计" width="120">
                <template #default="{ row }">¥ {{ (row.price * row.qty).toFixed(2) }}</template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-card>
    </template>

    <el-result v-else icon="info" title="未登录" sub-title="请点击右上角头像进行登录"/>
  </div>
</template>

<style scoped>
.wrap {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.panel {
  background: #ffffff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.profile {
  display: flex;
  gap: 20px;
  align-items: center;
  margin: 0 48px;
}

.avatar-col {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  min-width: 120px;
}

.avatar-preview {
  box-shadow: 0 2px 6px rgba(0, 0, 0, .08);
  cursor: pointer;
}

.avatar-hint {
  font-size: 12px;
  color: #6b7280;
}

.hidden-file {
  display: none;
}

.fields {
  flex: 1;
  padding-left: 16px;
  border-left: 1px solid #f0f2f5;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.user-form {
  width: 100%;
  max-width: 100%;
}

.user-form .el-input {
  width: 100%;
}

.actions-row {
  display: flex;
  gap: 12px;
  margin-top: 12px;
  width: 100%;
  justify-content: center;
}

.form-actions-inline {
  margin-bottom: 0;
}

.form-action-group {
  display: flex;
  gap: 12px;
  justify-content: center;
  width: 100%;
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
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
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

.thumb {
  width: 72px;
  height: 48px;
  border-radius: 6px;
  margin-right: 10px;
}

.item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.name {
  font-weight: 600;
}

.sku {
  color: #6b7280;
  font-size: 12px;
}

.right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.amount {
  font-weight: 700;
}

.items :deep(.el-table__inner-wrapper) {
  border-radius: 6px;
  overflow: hidden;
}

@media (max-width: 600px) {
  .user-form, .actions-row {
    max-width: 100%;
  }
}
</style>
