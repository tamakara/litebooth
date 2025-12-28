<script setup lang="ts">
import { ref } from "vue";
import { useOrder } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Refresh from "~icons/ep/refresh";
import PureTable from "@pureadmin/table";

defineOptions({
  name: "Order"
});

const formRef = ref();
const tableRef = ref();
const {
  form,
  loading,
  columns,
  dataList,
  pagination,
  onSearch,
  resetForm,
  handleSizeChange,
  handleCurrentChange
} = useOrder();

function onFullscreen() {
  tableRef.value.setAdaptive();
}
</script>

<template>
  <div class="main">
    <el-form
      ref="formRef"
      :inline="true"
      :model="form"
      class="search-form bg-bg_color w-full pl-8 pt-[12px] overflow-auto"
    >
      <el-form-item label="关键词：" prop="keyword">
        <el-input
          v-model="form.keyword"
          placeholder="请输入邮箱或商品名称"
          clearable
          class="w-[200px]!"
        />
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-select v-model="form.status" placeholder="请选择状态" clearable class="w-[180px]!">
          <el-option label="待付款" value="UNPAID" />
          <el-option label="已过期" value="EXPIRED" />
          <el-option label="已完成" value="FINISHED" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="useRenderIcon('ri/search-line')"
          :loading="loading"
          @click="onSearch"
        >
          搜索
        </el-button>
        <el-button :icon="useRenderIcon(Refresh)" @click="resetForm(formRef)">
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <PureTableBar
      title="订单管理"
      :columns="columns"
      @refresh="onSearch"
      @fullscreen="onFullscreen"
    >
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          ref="tableRef"
          adaptive
          :adaptiveConfig="{ offsetBottom: 130 }"
          align-whole="center"
          row-key="id"
          table-layout="auto"
          :loading="loading"
          :size="size"
          :data="dataList"
          :columns="dynamicColumns"
          :pagination="pagination"
          :paginationSmall="size === 'small'"
          :header-cell-style="{
            background: 'var(--el-fill-color-light)',
            color: 'var(--el-text-color-primary)'
          }"
          @page-size-change="handleSizeChange"
          @page-current-change="handleCurrentChange"
        >
          <template #status="{ row }">
            <el-tag v-if="row.status === 'UNPAID'" type="warning">待付款</el-tag>
            <el-tag v-else-if="row.status === 'EXPIRED'" type="info">已过期</el-tag>
            <el-tag v-else-if="row.status === 'FINISHED'" type="success">已完成</el-tag>
            <span v-else>{{ row.status }}</span>
          </template>
          <template #paymentMethod="{ row }">
            <el-tag v-if="row.paymentMethod === 'WXPAY'" type="success">微信支付</el-tag>
            <el-tag v-else-if="row.paymentMethod === 'ALIPAY'" type="primary">支付宝</el-tag>
            <span v-else>{{ row.paymentMethod }}</span>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
  </div>
</template>

<style scoped lang="scss">
:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>

