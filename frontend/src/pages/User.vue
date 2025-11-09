<script setup>
import { reactive, computed } from 'vue'
import { useUserStore } from '../stores/userStore'

const user = useUserStore()
const form = reactive({ username: '', password: '' })
const canSubmit = computed(() => form.username && form.password && !user.loading)

const submit = async () => {
  await user.login(form)
}
</script>

<template>
  <div class="wrap">
    <h2>用户登录与管理</h2>

    <el-row :gutter="12">
      <el-col :md="12" :sm="24">
        <el-card class="glass">
          <template #header>登录</template>
          <el-form label-width="72px" @submit.prevent>
            <el-form-item label="用户名">
              <el-input v-model="form.username" placeholder="输入用户名" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" type="password" show-password placeholder="输入密码" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="user.loading" :disabled="!canSubmit" @click="submit">登录</el-button>
              <el-button @click="form.username=''; form.password=''">重置</el-button>
            </el-form-item>
            <el-alert v-if="user.error" :title="user.error" type="error" show-icon />
          </el-form>
        </el-card>
      </el-col>

      <el-col :md="12" :sm="24">
        <el-card class="glass">
          <template #header>账户信息</template>
          <template v-if="user.isLogin">
            <el-descriptions :column="1" border>
              <el-descriptions-item label="用户名">{{ user.username }}</el-descriptions-item>
              <el-descriptions-item label="邮箱">{{ user.profile?.email }}</el-descriptions-item>
              <el-descriptions-item label="Token">{{ user.token }}</el-descriptions-item>
            </el-descriptions>
            <div class="ops">
              <el-button type="danger" @click="user.logout()">退出登录</el-button>
            </div>
          </template>
          <el-empty v-else description="未登录，请先在左侧登录" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.wrap { display:flex; flex-direction: column; gap: 12px; }
.ops { margin-top: 12px; }
.glass { background: rgba(255,255,255,.06); backdrop-filter: blur(8px); border: 1px solid rgba(255,255,255,.12); }
</style>

