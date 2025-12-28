<script setup lang="ts">
import { ref } from "vue";
import { useStock } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Refresh from "~icons/ep/refresh";
import AddFill from "~icons/ri/add-circle-line";
import Delete from "~icons/ep/delete";
import EditPen from "~icons/ep/edit-pen";
import PureTable from "@pureadmin/table";

defineOptions({
  name: "Stock"
});

const formRef = ref();
const tableRef = ref();
const {
  form,
  loading,
  columns,
  dataList,
  groupList,
  pagination,
  stockMap,
  stockLoadingMap,
  onSearch,
  resetForm,
  handleExpandChange,
  openAddStockDialog,
  openEditStockDialog,
  handleDeleteStock,
  handleSizeChange,
  handleCurrentChange
} = useStock();

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
      <el-form-item label="名称：" prop="keyword">
        <el-input
          v-model="form.keyword"
          placeholder="请输入商品名称"
          clearable
          class="w-[180px]!"
        />
      </el-form-item>
      <el-form-item label="商品组：" prop="groupId">
        <el-select v-model="form.groupId" placeholder="请选择商品组" clearable class="w-[180px]!">
          <el-option
            v-for="g in groupList"
            :key="g.id"
            :label="g.name"
            :value="g.id"
          />
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
      title="库存管理"
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
          :paginationSmall="size === 'small' ? true : false"
          :header-cell-style="{
            background: 'var(--el-fill-color-light)',
            color: 'var(--el-text-color-primary)'
          }"
          @page-size-change="handleSizeChange"
          @page-current-change="handleCurrentChange"
          @expand-change="handleExpandChange"
        >
          <template #operation="{ row }">
            <el-button
              class="reset-margin"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(AddFill)"
              @click="openAddStockDialog(row)"
            >
              添加库存
            </el-button>
          </template>
          <template #expand="{ row }">
            <div class="p-4">
               <el-table
                :data="stockMap[row.id] || []"
                v-loading="stockLoadingMap[row.id]"
                border
                style="width: 100%"
              >
                <el-table-column prop="id" label="ID" width="80" />
                <el-table-column prop="content" label="内容" />
                <el-table-column prop="isSold" label="状态" width="100">
                    <template #default="{ row: stockRow }">
                        <el-tag :type="stockRow.isSold ? 'danger' : 'success'">
                            {{ stockRow.isSold ? '已售出' : '未售出' }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createdAt" label="创建时间" width="180" />
                <el-table-column label="操作" width="180">
                  <template #default="{ row: stockRow }">
                    <el-button
                      link
                      type="primary"
                      :size="size"
                      :icon="useRenderIcon(EditPen)"
                      @click="openEditStockDialog(stockRow)"
                    >
                      修改
                    </el-button>
                    <el-popconfirm
                        title="是否确认删除?"
                        @confirm="handleDeleteStock(stockRow.id, row.id)"
                    >
                        <template #reference>
                            <el-button
                              link
                              type="danger"
                              :size="size"
                              :icon="useRenderIcon(Delete)"
                            >
                              删除
                            </el-button>
                        </template>
                    </el-popconfirm>
                  </template>
                </el-table-column>
              </el-table>
            </div>
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
