<script setup>
import {useRouter} from 'vue-router'
import {ref, reactive} from 'vue'
import {useUserStore} from './stores/userStore'
import {useProductStore} from './stores/productStore'

const router = useRouter()
const user = useUserStore()
const product = useProductStore()

const showAuth = ref(false)
const isRegister = ref(false)
const authForm = reactive({username: '', password: '', email: ''})

const submitAuth = async () => {
  if (isRegister.value) {
    await user.register(authForm)
  } else {
    await user.login(authForm)
  }
  if (user.isLogin) {
    showAuth.value = false
    router.push('/user')
  }
}
const switchMode = () => {
  isRegister.value = !isRegister.value;
  authForm.password = '';
}

const logout = () => {
  user.logout()
}
const handleSearch = () => {
  if (router.currentRoute.value.path !== '/') router.push('/')
}
</script>

<template>
  <el-container class="app-layout">
    <el-header class="app-header">
      <div class="nav-left" @click="$router.push('/')">
        <span class="logo-dot"></span>
        <span class="brand-name">LiteBooth 小店</span>
      </div>
      <div class="nav-center">
        <el-input v-model="product.keyword" placeholder="搜索商品名称 (回车)" clearable class="global-search"
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
              <el-avatar :size="40" class="avatar">
                {{ user.username.slice(0, 1).toUpperCase() }}
              </el-avatar>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/user')">修改信息</el-dropdown-item>
                <el-dropdown-item @click="router.push('/orders')">查询订单</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <template v-else>
          <el-avatar :size="40" class="avatar" style="background:#409eff; cursor:pointer" @click="showAuth = true">
            登
          </el-avatar>
        </template>
      </div>
    </el-header>

    <el-main class="app-main">
      <div class="announcement">
        <div class="anno-inner">
          <h4 class="anno-title">公告</h4>
          <p class="anno-text">
            <span class="emoji">🤖</span><strong>24小时自助下单</strong><span> - 有问题请右下角</span><span class="anno-highlight">在线咨询</span><span>或添加</span><span class="anno-highlight">售后客服</span><span>！</span>
          </p>
        </div>
      </div>
      <router-view />
    </el-main>

    <el-footer class="app-footer">
      <div>© {{ new Date().getFullYear() }} LiteBooth. 由 Element Plus & Vue3 驱动</div>
    </el-footer>

    <el-dialog v-model="showAuth" :title="isRegister ? '注册' : '登录'" width="420px" destroy-on-close>
      <el-form label-width="72px" @submit.prevent>
        <el-form-item label="用户名">
          <el-input v-model="authForm.username" autocomplete="off" placeholder="用户名"/>
        </el-form-item>
        <el-form-item v-if="isRegister" label="邮箱">
          <el-input v-model="authForm.email" type="text" placeholder="邮箱(可选)"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="authForm.password" type="password" show-password placeholder="密码"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="user.loading" @click="submitAuth">{{
              isRegister ? '注册并登录' : '登录'
            }}
          </el-button>
          <el-button text @click="switchMode">{{ isRegister ? '已有账号? 登录' : '没有账号? 注册' }}</el-button>
        </el-form-item>
        <el-alert v-if="user.error" :title="user.error" type="error" show-icon/>
      </el-form>
    </el-dialog>
  </el-container>
</template>

<style scoped>
.app-layout {
  min-height: 100vh;
}

.app-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 24px;
  background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
  border-bottom: 1px solid #e5e7eb;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .04);
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
}

.avatar-trigger {
  display: inline-flex;
  cursor: pointer;
}

.app-main {
  padding: 0;
}

.announcement {
  background: #fff;
  border: 2px solid #e5e7eb; /* 更轻的边框 */
  border-radius: 8px;        /* 更贴合整体卡片圆角 */
  padding: 20px 24px;
  margin: 16px 0;            /* 增加上下间距 */
  width: 100%;
  box-sizing: border-box;
}

.anno-inner {
  max-width: 100%;
}

.anno-title {
  margin: 0 0 8px;
  font-size: 20px;
  font-weight: 800;
  color: #111827;
}

.anno-text {
  margin: 0;
  font-size: 16px;
  line-height: 1.75;
  color: #343a40;
  text-align: center;
}

.anno-text .emoji {
  margin-right: 6px;
}

.anno-highlight {
  color: #e03e2d;
  font-weight: 700;
}

.app-footer {
  text-align: center;
  color: #6b7280;
  padding: 16px 0 32px;
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

.global-search {
  width: 520px;
}

@media (max-width: 920px) {
  .global-search {
    width: 100%;
  }

  .app-header {
    flex-wrap: wrap;
  }

  .nav-center {
    order: 3;
    width: 100%;
  }
}
</style>
