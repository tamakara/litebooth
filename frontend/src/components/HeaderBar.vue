<script setup>
import {toRefs} from 'vue'
import {useRouter} from 'vue-router'
import {useHomeStore} from '@/stores/homeStore.ts'

const router = useRouter()
const home = useHomeStore()

const {itemCardQueryForm: queryForm} = toRefs(home)

const handleSearch = async () => {
  queryForm.value.pageNum = 1
  await home.fetchItemCardPageVO()
}
</script>

<template>
  <el-header class="app-header">
    <div class="nav-left">
      <div class="brand" @click="router.push('/')">
        <span class="logo-dot"></span>
        <span class="brand-name">LiteBooth</span>
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
  padding: 14px 32px;
  border-radius: 8px;
  background: #ffffff;
  border-bottom: 1px solid #e2e5e9;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  gap: 24px;
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.brand {
  display: flex;
  align-items: center;
  gap: 12px;
  font-weight: 700;
  color: #111827;
  cursor: pointer;
}

.nav-links {
  display: flex;
  align-items: center;
  gap: 16px;
}

.nav-link {
  font-size: 15px;
  color: #4b5563;
  cursor: pointer;
  padding: 6px 10px;
  border-radius: 999px;
  transition: all 0.2s ease;
}

.nav-link:hover {
  color: #111827;
  background: #f3f4f6;
}

.nav-right {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.global-search {
  width: 560px;
  max-width: 100%;
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

.global-search :deep(.el-input__wrapper) {
  height: 46px;
}

.global-search :deep(.el-input__inner) {
  font-size: 16px;
}

.global-search :deep(.el-input-group__append .el-button) {
  height: 46px;
  font-size: 16px;
}

@media (max-width: 920px) {
  .app-header {
    flex-wrap: wrap;
    padding: 10px 16px;
  }

  .nav-left {
    width: 100%;
    justify-content: space-between;
  }

  .nav-right {
    width: 100%;
    justify-content: center;
  }

  .global-search {
    width: 100%;
    max-width: 100%;
  }
}
</style>
