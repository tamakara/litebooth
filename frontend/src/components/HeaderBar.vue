<script setup>
import {computed, ref} from 'vue'
import {useRouter} from 'vue-router'
import {useUserStore} from '../stores/userStore'
import {useHomeStore} from '../stores/homeStore'
import AuthDialog from './AuthDialog.vue'
import defaultAvatar from '../assets/default_avatar.png'

const router = useRouter()
const user = useUserStore()
const home = useHomeStore()

const searchForm = computed(() => home.searchForm)

const showAuth = ref(false)
const onLoginSuccess = () => {
  showAuth.value = false
}

const handleSearch = () => {
  searchForm.pageNum = 1
  home.fetchItemInfo()
}
</script>

<template>
  <el-header class="app-header">
    <div class="nav-left" @click="router.push('/')">
      <span class="logo-dot"></span>
      <span class="brand-name">LiteBooth</span>
    </div>
    <div class="nav-center">
      <el-input
          v-model="searchForm.keyword"
          placeholder="搜索商品名称"
          clearable
          class="global-search"
          size="large"
          @keydown.enter="handleSearch"
      >
        <template #append>
          <el-button
              type="primary"
              size="large"
              @click="handleSearch"
          >
            搜索
          </el-button>
        </template>
      </el-input>
    </div>
    <div class="nav-right">
      <template v-if="user.isLogin">
        <div class="user-box" role="button" @click="router.push('/user')">
          <el-avatar
              :size="40"
              class="avatar"
              :src="user.profile?.avatar || defaultAvatar"
          />
          <span class="username">{{ user.username }}</span>
        </div>
      </template>
      <template v-else>
        <div
            class="user-box"
            role="button"
            aria-label="未登录，点击登录"
            @click="showAuth = true"
        >
          <el-avatar :size="40" class="avatar" :src="defaultAvatar"/>
          <span class="username">未登录</span>
        </div>
      </template>
    </div>
    <AuthDialog v-model:visible="showAuth" @success="onLoginSuccess"/>
  </el-header>
</template>

<style scoped>
.app-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 32px;
  border-radius: 8px;
  background: #ffffff;
  border-bottom: 1px solid #e2e5e9;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 12px;
  font-weight: 700;
  color: #111827;
  cursor: pointer;
}

.nav-center {
  flex: 1;
  display: flex;
  justify-content: center;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.global-search {
  width: 560px;
  max-width: 65%;
  border-radius: 10px;
}

.brand-name {
  font-size: 22px;
  letter-spacing: 0.3px;
}

.logo-dot {
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff, #67c23a);
  box-shadow: 0 0 0 6px rgba(64, 158, 255, 0.18);
}

.avatar {
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.12);
}

.username {
  font-weight: 600;
  color: #333;
  cursor: pointer;
  max-width: 160px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.user-box {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  padding: 6px 14px 6px 8px;
  border: 1px solid #e5e7eb;
  border-radius: 999px;
  background: #fff;
  cursor: pointer;
  transition: all 0.2s ease;
}

.user-box:hover {
  border-color: #cfd4dc;
  background: #f9fafb;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.06);
}

.user-box:active {
  transform: translateY(1px);
}

/* 放大输入框视觉高度与字号，匹配用户区块高度 */
.global-search :deep(.el-input__wrapper) {
  height: 46px;
}

.global-search :deep(.el-input__inner) {
  font-size: 16px;
}

/* 追加区按钮与输入框等高 */
.global-search :deep(.el-input-group__append .el-button) {
  height: 46px;
  font-size: 16px;
}

@media (max-width: 920px) {
  .app-header {
    flex-wrap: wrap;
    padding: 10px 16px;
  }

  .nav-center {
    order: 3;
    width: 100%;
  }

  .global-search {
    width: 100%;
    max-width: 100%;
  }
}
</style>
