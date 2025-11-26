<script setup>
import {reactive, watchEffect, computed, ref, onMounted} from 'vue'
import {useUserStore} from '../stores/userStore.js'
import {ElMessage} from 'element-plus'

const user = useUserStore()

const orderList = computed(() => user.orderInfoPage)
const searchForm = computed(() => user.queryForm)

const fileInput = ref(null)
const emailDialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const emailEdit = reactive({oldEmail: '', newEmail: '', code: ''})
const passwordEdit = reactive({oldPassword: '', newPassword: '', code: ''})

const cardDialogVisible = ref(false)
const currentCardContentList = ref('')

watchEffect(() => {
  if (user.profile) {
    emailEdit.oldEmail = user.profile.email || ''
  }
})

onMounted(async () => {
  await user.fetchOrderInfoPageVO()
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
  user.updateEmail(emailEdit.newEmail)
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

const fmt = (d) => d ? new Date(d).toLocaleString() : ''
const statusType = (s) => s && s.includes('待') ? 'warning' : (s && s.includes('完成') ? 'success' : 'info')

// 显示卡密
const showCard = (order) => {
  currentCardContentList.value = order?.contentList || '暂无卡密信息'
  cardDialogVisible.value = true
}

// 分页变更处理
const handlePageChange = () => {
  if (!user.isLogin) return
  user.fetchOrderInfoPageVO()
}
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
        <el-empty v-if="! orderList.orderInfoList.length" description="暂无订单"/>
        <div v-else class="order-list">
          <div v-for="o in orderList.orderInfoList" :key="o.id" class="order-item">
            <div class="order-head">
              <div class="left">
                <div class="ono">订单号：<span class="mono">{{ o.id }}</span></div>
                <div class="date">创建时间：{{ fmt(o.createdAt) }}</div>
                <div class="date">付款时间：{{ fmt(o.createdAt) }}</div>
                <div class="field">商品名称：{{ o.itemName }}</div>
                <div class="field">数量：{{ o.quantity }}</div>
                <div class="field">支付方式：{{ o.payMethod }}</div>
              </div>
              <div class="right">
                <el-tag :type="statusType(o.status)">{{ o.status }}</el-tag>
                <div class="amount">合计：¥ {{ Number(o.amount || 0).toFixed(2) }}</div>
                <el-button type="primary" size="small" @click="showCard(o)">显示卡密</el-button>
              </div>
            </div>
          </div>
        </div>
        <!-- 分页组件 -->
        <div v-if="orderList.total > 0" class="pagination-wrapper">
          <el-pagination
              layout="prev, pager, next"
              :current-page="searchForm.pageNum"
              :page-size="searchForm.pageSize"
              :total="orderList.total"
              @current-change="handlePageChange"
          />
        </div>
      </el-card>

      <!-- 卡密弹窗 -->
      <el-dialog v-model="cardDialogVisible" title="卡密内容" width="500px">
        <pre v-for="content in currentCardContentList" class="card-content">{{ content }}</pre>
        <template #footer>
          <el-button type="primary" @click="cardDialogVisible = false">关闭</el-button>
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

.plain-value {
  color: #606266;
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
