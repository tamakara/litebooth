<script setup>
import {computed, onMounted, ref, toRefs} from 'vue'
import {useUserStore} from '@/stores/userStore.ts'
import {ElMessage} from 'element-plus'
import EmailUpdateDialog from '@/components/EmailUpdateDialog.vue'
import PasswordUpdateDialog from '@/components/PasswordUpdateDialog.vue'
import {formatDate} from "@/utils/index.js";

const user = useUserStore()

const {orderInfoPage, queryForm} = toRefs(user)

const orderInfoList = computed(() => orderInfoPage.value.orderInfoList)
const total = computed(() => orderInfoPage.value.total)

const fileInput = ref(null)

const emailDialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const contentListDialogVisible = ref(false)
const currentContentList = ref([])

const triggerAvatar = () => {
  fileInput.value.click()
}

const onAvatarChange = async (e) => {
  const file = e.target.files?.[0]
  if (!file) return
  await user.updateAvatar(file)
  ElMessage.success('头像已更新')
  e.target.value = ''
}

const openEmailDialog = () => {
  emailDialogVisible.value = true
}

const openPasswordDialog = () => {
  passwordDialogVisible.value = true
}

const handleEmailSubmit = async (form) => {
  await user.updateEmail(form)
  ElMessage.success('邮箱已更新')
  emailDialogVisible.value = false
}

const handlePasswordSubmit = async (form) => {
  await user.updatePassword(form)
  ElMessage.success('密码已更新')
  passwordDialogVisible.value = false
}

const openContentListDialog = (contentList) => {
  currentContentList.value = contentList
  contentListDialogVisible.value = true
}

const handlePageChange = async () => {
  if (!user.isLogin) return
  await user.fetchOrderInfoPageVO()
}

const handleSendEmailCode = (email,type) => {
  // TODO: 在这里实现发送邮箱验证码的逻辑（例如调用 user 相关的 API）
  // user.sendEmailCode({ newEmail })
}


const statusType = (s) => s && s.includes('待') ? 'warning' : (s && s.includes('完成') ? 'success' : 'info')

onMounted(async () => {
  await user.fetchOrderInfoPageVO()
})
</script>

<template>
  <div class="wrap">
    <h2>用户信息</h2>
    <template v-if="user.isLogin">
      <el-card class="panel">
        <template #header>基本资料</template>
        <div class="profile">
          <div class="avatar-col">
            <el-avatar :size="80" :src="user.profile?.avatar" class="avatar-preview" @click="triggerAvatar"/>
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

      <EmailUpdateDialog
          v-model:visible="emailDialogVisible"
          :old-email="user.profile.email"
          @submit="handleEmailSubmit"
          @send-code="handleSendEmailCode"
      />
      <PasswordUpdateDialog
          v-model:visible="passwordDialogVisible"
          @submit="handlePasswordSubmit"
          @send-code="handleSendEmailCode"
      />

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
