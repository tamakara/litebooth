<script setup>
import {toRefs} from 'vue'
import {useRouter} from 'vue-router'
import {useHomeStore} from '@/store/homeStore'

const router = useRouter()
const home = useHomeStore()

const {shopInfo, itemCardQueryForm: queryForm} = toRefs(home)

const handleSearch = async () => {
  queryForm.value.pageNum = 1
  await home.fetchItemCardPageVO()
}
</script>

<template>
  <el-header class="app-header">
    <div class="nav-left">
      <div class="brand" @click="router.push('/')">
        <img
          class="logo"
          :src="shopInfo.logoFileUrl"
          :alt="shopInfo.logoTitle"
          loading="lazy"
        />
        <span class="logo-title">{{ shopInfo.logoTitle }}</span>
      </div>
      <nav class="nav-links">
        <span
            class="nav-link"
            role="button"
            @click="router.push('/')"
        >
          首页
        </span>
        <span
            class="nav-link"
            role="button"
            @click="router.push('/order')"
        >
          订单查询
        </span>
      </nav>
    </div>

    <div class="nav-right">
      <el-input
          v-if="router.currentRoute.value.name==='home'"
          v-model="queryForm.keyword"
          @keydown.enter="handleSearch"
          class="global-search"
          placeholder="搜索商品名称"
          clearable
          size="large"
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
  padding: 0 24px;
  height: 72px;
  border-radius: var(--border-radius-base);
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(229, 231, 235, 0.5);
  box-shadow: var(--shadow-sm);
  gap: 24px;
  margin-bottom: 32px;
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 32px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  font-weight: 700;
  color: var(--color-text-main);
  cursor: pointer;
  transition: opacity 0.2s;
}

.brand:hover {
  opacity: 0.8;
}

.logo {
  height: 36px;
  width: auto;
}

.logo-title {
  font-size: 20px;
  letter-spacing: -0.025em;
}

.nav-links {
  display: flex;
  gap: 24px;
}

.nav-link {
  font-size: 15px;
  font-weight: 500;
  color: var(--color-text-secondary);
  cursor: pointer;
  transition: color 0.2s;
}

.nav-link:hover {
  color: var(--color-primary);
}

.nav-right {
  flex: 1;
  max-width: 400px;
}

.global-search :deep(.el-input__wrapper) {
  border-radius: 20px 0 0 20px;
  box-shadow: none;
  background: #f9fafb;
  border: 1px solid #e5e7eb;
}

.global-search :deep(.el-input__wrapper.is-focus) {
  border-color: var(--color-primary);
  background: #fff;
}

.global-search :deep(.el-input-group__append) {
  border-radius: 0 20px 20px 0;
  background: var(--color-primary);
  border: none;
  box-shadow: none;
}

.global-search :deep(.el-button) {
  color: #fff;
  border: none;
  border-radius: 0 20px 20px 0;
}

@media (max-width: 768px) {
  .app-header {
    padding: 0 16px;
    height: auto;
    flex-direction: column;
    align-items: stretch;
    gap: 16px;
    padding-bottom: 16px;
  }

  .nav-left {
    justify-content: space-between;
  }

  .nav-right {
    max-width: none;
  }
}
</style>
