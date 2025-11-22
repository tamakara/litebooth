<script setup>
import {reactive, watchEffect, computed, ref} from 'vue'
import {useUserStore} from '../stores/userStore'
import {ElMessage} from 'element-plus'

const user = useUserStore()

const fileInput = ref(null)

const emailDialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const emailEdit = reactive({oldEmail: '', newEmail: '', code: ''})
const passwordEdit = reactive({oldPassword: '', newPassword: '', code: ''})

watchEffect(() => {
  if (user.profile) {
    emailEdit.oldEmail = user.profile.email || ''
  }
})

const triggerAvatar = () => {
  fileInput.value?.click()
}
const onAvatarChange = async (e) => {
  const file = e.target.files?.[0]
  if (!file) return
  await user.updateAvatar(file)
  ElMessage.success('头像已更新')
  e.target.value = ''
}

const openEmailDialog = () => {
  if (!user.isLogin) return
  emailEdit.oldEmail = user.profile?.email || ''
  emailEdit.newEmail = ''
  emailEdit.code = ''
  emailDialogVisible.value = true
}
const openPasswordDialog = () => {
  if (!user.isLogin) return
  passwordEdit.oldPassword = ''
  passwordEdit.newPassword = ''
  passwordEdit.code = ''
  passwordDialogVisible.value = true
}

const sendEmailCode = (scene) => {
  ElMessage.success(`验证码已发送（${scene}）`)
}

const submitEmailChange = () => {
  if (!user.profile) return
  if (emailEdit.oldEmail !== user.profile.email) {
    ElMessage.error('旧邮箱不匹配')
    return
  }
  if (!emailEdit.newEmail) {
    ElMessage.error('请输入新邮箱')
    return
  }
  if (!emailEdit.code) {
    ElMessage.error('请输入验证码')
    return
  }
  user.updateEmail(mailEdit.newEmail)
  ElMessage.success('邮箱已更新')
  emailDialogVisible.value = false
}

const submitPasswordChange = () => {
  if (!passwordEdit.oldPassword || !passwordEdit.newPassword) {
    ElMessage.error('请填写旧密码与新密码')
    return
  }
  if (!passwordEdit.code) {
    ElMessage.error('请输入邮箱验证码')
    return
  }
  ElMessage.success('密码已更新')
  passwordDialogVisible.value = false
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
            <el-avatar :size="80" :src="user.profile?.avatar" class="avatar-preview" @click="triggerAvatar" />
            <div class="avatar-hint">点击更换头像</div>
            <input ref="fileInput" type="file" accept="image/*" class="hidden-file" @change="onAvatarChange"/>
          </div>
          <div class="fields">

            <div class="info-row">
              <div class="label">用户名</div>
              <div class="value">{{ user.profile?.username }}</div>
            </div>

            <div class="info-row">
              <div class="label">邮箱</div>
              <div class="value">
                <span>{{ user.profile?.email }}</span>
              </div>
            </div>
            <div class="logout-row">
              <el-button type="primary" @click="openEmailDialog">修改邮箱</el-button>
              <el-button type="primary" @click="openPasswordDialog">修改密码</el-button>
              <el-button type="danger" @click="user.logout()">退出登录</el-button>
            </div>
          </div>
        </div>
      </el-card>

      <el-dialog v-model="emailDialogVisible" title="修改邮箱" width="420px">
        <el-form label-width="90px">
          <el-form-item label="旧邮箱">
            <div class="plain-value">{{ emailEdit.oldEmail }}</div>
          </el-form-item>
          <el-form-item label="新邮箱">
            <el-input v-model="emailEdit.newEmail" placeholder="输入新邮箱"/>
          </el-form-item>
          <el-form-item label="验证码">
            <el-input v-model="emailEdit.code" placeholder="输入验证码">
              <template #append>
                <el-button @click="sendEmailCode('邮箱修改')">发送</el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="emailDialogVisible=false">取消</el-button>
          <el-button type="primary" @click="submitEmailChange">确定</el-button>
        </template>
      </el-dialog>

      <!-- 密码编辑弹窗 -->
      <el-dialog v-model="passwordDialogVisible" title="修改密码" width="420px">
        <el-form label-width="90px">
          <el-form-item label="旧密码">
            <el-input v-model="passwordEdit.oldPassword" type="password" placeholder="输入旧密码"/>
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="passwordEdit.newPassword" type="password" placeholder="输入新密码"/>
          </el-form-item>
          <el-form-item label="验证码">
            <el-input v-model="passwordEdit.code" placeholder="邮箱验证码">
              <template #append>
                <el-button @click="sendEmailCode('密码修改')">发送</el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="passwordDialogVisible=false">取消</el-button>
          <el-button type="primary" @click="submitPasswordChange">确定</el-button>
        </template>
      </el-dialog>

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
  align-items: stretch;
  gap: 10px;
}

.info-row {
  display: flex;
  align-items: center;
  gap: 16px;
}

.info-row .label {
  width: 96px;
  color: #555;
  font-weight: 500;
}

.info-row .value {
  flex: 1;
  display: flex;
  align-items: center;
}

.logout-row {
  margin-top: 4px;
  display: flex;
  gap: 10px;
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

.plain-value {
  color: #606266;
}
</style>
