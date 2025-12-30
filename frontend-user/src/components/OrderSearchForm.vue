<script setup lang="ts">
import {PropType} from 'vue'
import {OrderInfoPageQueryFormDTO} from "@/types";

const props = defineProps({
  modelValue: {
    type: Object as PropType<OrderInfoPageQueryFormDTO>,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:modelValue', 'search', 'reset'])

const onSearch = () => {
  emit('search')
}

const onReset = () => {
  emit('reset')
}
</script>

<template>
  <div>
    <!-- 查询方式 -->
    <div class="search-mode-toggle">
      <el-radio-group v-model="modelValue.queryMode" size="large">
        <el-radio-button label="orderId">按订单号查询</el-radio-button>
        <el-radio-button label="queryEmail">按邮箱查询</el-radio-button>
      </el-radio-group>
    </div>

    <el-form :model="modelValue" class="query-form" label-width="80px">
      <!-- 订单号查询 -->
      <template v-if="modelValue.queryMode === 'orderId'">
        <el-form-item label="订单号">
          <el-input
              v-model="modelValue.orderId"
              placeholder="请输入订单号"
              clearable
              size="large"
          />
        </el-form-item>
      </template>

      <!-- 邮箱查询 -->
      <template v-else>
        <el-form-item label="查询邮箱">
          <el-input
              v-model="modelValue.queryEmail"
              placeholder="请输入查询邮箱"
              clearable
              size="large"
          />
        </el-form-item>
        <el-form-item label="查询密码">
          <el-input
              v-model="modelValue.queryPassword"
              placeholder="请输入查询密码"
              show-password
              clearable
              size="large"
          />
        </el-form-item>
      </template>

      <el-form-item>
        <div class="btn-row">
          <el-button
              type="primary"
              size="large"
              @click="onSearch"
              :loading="loading"
          >
            查询
          </el-button>
          <el-button
              size="large"
              @click="onReset"
              :loading="loading"
          >
            重置
          </el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped>
.search-mode-toggle {
  margin-bottom: 16px;
  padding: 8px 12px;
  border-radius: 6px;
}

.query-form {
  margin-bottom: 20px;
}

.btn-row {
  display: flex;
  gap: 12px;
}
</style>

