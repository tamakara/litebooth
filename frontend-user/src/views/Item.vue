<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {useRouter} from 'vue-router'
import OrderInfoDialog from '@/components/OrderInfoDialog.vue'
import ContentDialog from '@/components/ContentDialog.vue'
import ItemDetail from '@/components/ItemDetail.vue'
import OrderForm from '@/components/OrderForm.vue'
import {useItemStore} from "@/store/itemStore";
import {storeToRefs} from "pinia";

const router = useRouter()
const itemStore = useItemStore()
const {itemInfo, captchaInfo, orderCreateForm, orderInfo, loading} = storeToRefs(itemStore)
const {fetchCaptchaInfoVO, verifyCaptchaCode, fetchItemInfoVO, createOrder, payOrder, clearOrderCreateForm} = itemStore

const orderDialogVisible = ref(false)
const contentVisible = ref(false)

const verifyCaptchaWrapper = async (code: string) => {
  orderCreateForm.value.captchaCode = code
  return await verifyCaptchaCode()
}

const onBuyClick = async () => {
  await createOrder()
  clearOrderCreateForm()
  orderDialogVisible.value = true
}

const onPayOrderClick = async () => {
  if (orderInfo.value) {
    await payOrder()
  }
}

const onViewContentClick = () => {
  contentVisible.value = true
}

onMounted(async () => {
  const itemId = router.currentRoute.value.params.id as string
  await fetchItemInfoVO(itemId)
  await fetchCaptchaInfoVO()
})
</script>

<template>
  <div class="page">
    <!-- 商品面板 -->
    <el-card class="panel main-panel" v-if="loading">
      <div class="main-body">
        <div class="cover-col">
          <el-skeleton animated>
            <template #template>
              <el-skeleton-item variant="image" style="width: 100%; height: 320px; border-radius: 8px" />
            </template>
          </el-skeleton>
        </div>
        <div class="info-col-wrapper">
          <el-skeleton animated :rows="8" />
        </div>
      </div>
    </el-card>

    <el-card class="panel main-panel" v-else>
      <div class="main-body">
        <div class="cover-col">
          <el-image
              :src="itemInfo.coverFileUrl"
              fit="cover"
              class="cover-img"
              :preview-src-list="[itemInfo.coverFileUrl]"
          />
        </div>

        <div class="info-col-wrapper">
          <ItemDetail :item-info="itemInfo"/>

          <!-- 下单表单 -->
          <OrderForm
              v-model="orderCreateForm"
              :item-info="itemInfo"
              :captcha-info="captchaInfo"
              :verify-captcha="verifyCaptchaWrapper"
              @refreshCaptcha="fetchCaptchaInfoVO"
              @submit="onBuyClick"
              @reset="clearOrderCreateForm"
          />
        </div>
      </div>
    </el-card>

    <!-- 商品介绍 -->
    <el-card class="panel desc-panel">
      <template #header>
        <div class="panel-header">
          <h3 class="title small">商品介绍</h3>
        </div>
      </template>
      <div class="desc-body">
        <div class="rich-text" v-html="itemInfo.description"/>
      </div>
    </el-card>

    <!-- 订单弹窗 -->
    <OrderInfoDialog
        v-if="orderInfo"
        v-model:visible="orderDialogVisible"
        :order-info="orderInfo"
        @pay="onPayOrderClick"
        @viewContent="onViewContentClick"
    />

    <!-- 卡密弹窗 -->
    <ContentDialog
        v-if="orderInfo"
        v-model:visible="contentVisible"
        :content-list="orderInfo.contentList"
    />
  </div>
</template>

<style scoped>
.page {
  max-width: 980px;
  margin: 32px auto 0;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.panel {
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 8px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title.small {
  font-size: 16px;
  margin: 0;
}

.main-body {
  display: flex;
  gap: 24px;
}

.cover-col {
  width: 320px;
  flex-shrink: 0;
}

.cover-img {
  width: 100%;
  border-radius: 8px;
}

.info-col-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.desc-body {
  padding: 4px 0;
}

.rich-text :deep(img) {
  max-width: 100%;
}

.rich-text :deep(p) {
  line-height: 1.8;
}

@media (max-width: 768px) {
  .main-body {
    flex-direction: column;
  }

  .cover-col {
    width: 100%;
  }
}
</style>
