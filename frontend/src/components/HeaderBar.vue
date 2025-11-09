<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {useUserStore} from '../stores/userStore'
import {useItemStore} from '../stores/itemStore'
import AuthDialog from './AuthDialog.vue'

const router = useRouter()
const user = useUserStore()
const item = useItemStore()

const showAuth = ref(false)
const onLoginSuccess = () => {
  showAuth.value = false;
  router.push('/user')
}

const handleSearch = () => {
  if (router.currentRoute.value.path !== '/') router.push('/')
}
const logout = () => user.logout()
</script>

<template>
  <el-header class="app-header">
    <div class="nav-left" @click="router.push('/')">
      <span class="logo-dot"></span>
      <span class="brand-name">LiteBooth</span>
    </div>
    <div class="nav-center">
      <el-input v-model="item.keyword" placeholder="搜索商品名称 (回车)" clearable class="global-search"
                @keydown.enter="handleSearch">
        <template #append>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
    </div>
    <div class="nav-right">
      <template v-if="user.isLogin">
        <el-dropdown trigger="hover" :hide-on-click="false">
          <span class="avatar-trigger">
            <el-avatar :size="40" class="avatar">{{ user.username.slice(0, 1).toUpperCase() }}</el-avatar>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item  @click="logout">退出账号</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
      <template v-else>
        <el-avatar :size="40" class="avatar" style="background:#409eff; cursor:pointer" @click="showAuth = true">登
        </el-avatar>
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
  padding: 10px 28px;
  border-radius: 4px;
  background: #ffffff;
  border-bottom: 1px solid #e2e5e9;
  box-shadow: 0 2px 6px rgba(0, 0, 0, .05);
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 10px;
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
  width: 520px;
  max-width: 60%;
  border-radius: 8px;
}

.logo-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff, #67c23a);
  box-shadow: 0 0 0 4px rgba(64, 158, 255, .15);
}

.avatar {
  box-shadow: 0 2px 6px rgba(0, 0, 0, .12);
}

.avatar-trigger {
  display: inline-flex;
  cursor: pointer;
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
