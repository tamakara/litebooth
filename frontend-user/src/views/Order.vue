<script setup lang="ts">
import {computed, onMounted, ref, toRefs} from 'vue'
import { useOrderStore} from '@/store/orderStore'
import ContentDialog from '@/components/ContentDialog.vue'
import OrderSearchForm from '@/components/OrderSearchForm.vue'
import OrderList from '@/components/OrderList.vue'

const order = useOrderStore()

const {orderInfoPage, queryForm} = toRefs(order)
const orderInfoList = computed(() => orderInfoPage.value.records)
const total = computed(() => orderInfoPage.value.total)
const contentListDialogVisible = ref(false)
const currentContentList = ref<string[]>([])

const openContentListDialog = (contentList?: string[]) => {
  currentContentList.value = contentList || []
  contentListDialogVisible.value = true
}

const handlePageChange = async (page: number) => {
  queryForm.value.pageNum = page
  await order.fetchOrderInfoPageVO()
}

const onSearch = async () => {
  queryForm.value.pageNum = 1
  await order.fetchOrderInfoPageVO()
}

const onReset = async () => {
  order.resetQueryForm()
  await order.fetchOrderInfoPageVO()
}

const handlePay = async (orderId: string) => {
  await order.payExistingOrder(orderId)
}

onMounted(async () => {

})
</script>

<template>
  <el-card class="panel page">
    <template #header>订单查询</template>

    <OrderSearchForm
        v-model="queryForm"
        :loading="order.loading"
        @search="onSearch"
        @reset="onReset"
    />

    <div v-if="order.loading" class="order-list-skeleton">
      <el-card v-for="n in 3" :key="n" shadow="never" style="margin-bottom: 12px; border: 1px dashed #e5e7eb">
        <el-skeleton animated>
          <template #template>
            <div style="display: flex; justify-content: space-between;">
              <div style="width: 60%">
                <el-skeleton-item variant="text" style="width: 40%; margin-bottom: 8px" />
                <el-skeleton-item variant="text" style="width: 60%; margin-bottom: 8px" />
                <el-skeleton-item variant="text" style="width: 50%" />
              </div>
              <div style="width: 30%; display: flex; flex-direction: column; align-items: flex-end">
                <el-skeleton-item variant="text" style="width: 40%; margin-bottom: 8px" />
                <el-skeleton-item variant="button" style="width: 60px" />
              </div>
            </div>
          </template>
        </el-skeleton>
      </el-card>
    </div>

    <OrderList
        v-else
        :order-list="orderInfoList"
        @pay="handlePay"
        @viewContent="openContentListDialog"
    />

    <!-- 分页组件 -->
    <div v-if="total > 0" class="pagination-wrapper">
      <el-pagination
          layout="prev, pager, next"
          :current-page="queryForm.pageNum"
          :page-size="queryForm.pageSize"
          :total="total"
          @current-change="handlePageChange"
      />
    </div>
  </el-card>

  <!-- 卡密弹窗 -->
  <ContentDialog
      v-model:visible="contentListDialogVisible"
      :content-list="currentContentList"
  />
</template>

<style scoped>

.page {
  max-width: 980px;
  margin: 32px auto 0;
  flex-direction: column;
  gap: 16px;
}

.panel {
  background: #ffffff;
  border: 1px solid #ebeef5;
  border-radius: 8px;
}

.pagination-wrapper {
  margin-top: 12px;
  display: flex;
  justify-content: center;
}
</style>
