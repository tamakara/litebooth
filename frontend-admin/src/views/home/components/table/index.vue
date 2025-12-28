<script setup lang="ts">
import { useColumns } from "./columns";
import Empty from "./empty.svg?component";
import { watch } from "vue";

const props = defineProps<{
  data: any[];
}>();

const { loading, columns, pagination, onCurrentChange } = useColumns();

watch(
  () => props.data,
  newVal => {
    if (newVal) {
      pagination.total = newVal.length;
    }
  },
  { immediate: true }
);
</script>

<template>
  <pure-table
    row-key="id"
    alignWhole="center"
    showOverflowTooltip
    :loading="loading"
    :loading-config="{ background: 'transparent' }"
    :data="
      props.data.slice(
        (pagination.currentPage - 1) * pagination.pageSize,
        pagination.currentPage * pagination.pageSize
      )
    "
    :columns="columns"
    :pagination="pagination"
    @page-current-change="onCurrentChange"
  >
    <template #empty>
      <el-empty description="暂无数据" :image-size="60">
        <template #image>
          <Empty />
        </template>
      </el-empty>
    </template>
  </pure-table>
</template>

<style lang="scss">
.pure-table-filter {
  .el-table-filter__list {
    min-width: 80px;
    padding: 0;

    li {
      line-height: 28px;
    }
  }
}
</style>

<style lang="scss" scoped>
:deep(.el-table) {
  --el-table-border: none;
  --el-table-border-color: transparent;

  .el-empty__description {
    margin: 0;
  }

  .el-scrollbar__bar {
    display: none;
  }
}
</style>
