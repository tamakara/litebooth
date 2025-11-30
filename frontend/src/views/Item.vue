<script setup>
import {useItemStore} from '@/stores/itemStore.js'
import {onMounted, ref, toRefs} from 'vue'
import {useRouter} from 'vue-router'
import OrderInfoDialog from '@/components/OrderInfoDialog.vue'

const item = useItemStore()
const router = useRouter()

const {itemInfo, captchaInfo, orderCreateForm, orderInfo, loading} = toRefs(item)
const orderDialogVisible = ref(false)
const formRef = ref()

const rules = {
  email: [
    {
      message: '请输入收货邮箱',
      trigger: 'blur',
      required: true,
    },
    {
      type: 'email',
      message: '邮箱格式不正确',
      trigger: ['blur', 'change']
    }
  ],
  queryPassword: [
    {
      message: '请输入订单查询密码',
      trigger: 'blur',
      required: true,
    },
    {
      message: '查询密码至少 4 位',
      trigger: 'blur',
      min: 4,
    }
  ],
  captchaCode: [
    {
      message: '请输入图形验证码',
      trigger: 'blur',
      required: true,
    }
  ],
}

const refreshCaptcha = async () => {
  await item.fetchCaptchaInfoVO()
}

const onBuyClick = async () => {
  if (!formRef.value) {
    await item.createOrder()
    orderDialogVisible.value = true
    return
  }

  await formRef.value.validate(async (valid) => {
    if (!valid) return
    await item.createOrder()
    orderDialogVisible.value = true
  })
}

const onPayOrderClick = async () => {
  await item.payOrder()
  orderDialogVisible.value = false
  await router.push({name: 'user'})
}

onMounted(async () => {
  await item.fetchItemInfoVO(router.currentRoute.value.params.id)
  await item.fetchCaptchaInfoVO()
})
</script>

<template>
  <div class="page" v-loading="loading">
    <!-- 商品面板 -->
    <el-card class="panel main-panel">
      <div class="main-body">
        <div class="cover-col">
          <el-image
              :src="itemInfo.cover"
              fit="cover"
              class="cover-img"
              :preview-src-list="[itemInfo.cover]"
          />
        </div>

        <div class="info-col">
          <div class="item-name">{{ itemInfo.name }}</div>

          <div class="price-row">
            <div class="price">¥ {{ Number(itemInfo.price).toFixed(2) }}</div>
            <div class="stock-line">
              <span v-if="itemInfo.stock > 0">库存: {{ itemInfo.stock }}</span>
              <span v-else style="color: red; font-weight: bold;">商品缺货</span>
            </div>
          </div>

          <!-- 下单表单 -->
          <el-form
              ref="formRef"
              :model="orderCreateForm"
              :rules="rules"
              label-width="90px"
          >
            <el-form-item label="购买数量" prop="quantity">
              <el-input-number
                  v-if="itemInfo.stock > 0"
                  v-model="orderCreateForm.quantity"
                  :min="1"
                  :max="itemInfo.stock"
              />
              <el-input-number
                  v-else
                  v-model="orderCreateForm.quantity"
                  :min="0"
                  :max="0"
              />
            </el-form-item>

            <el-form-item label="支付方式" prop="payMethod">
              <el-radio-group v-model="orderCreateForm.payMethod" :disabled="itemInfo.stock === 0">
                <el-radio label="wxpay">微信</el-radio>
                <el-radio label="alipay">支付宝</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="收货邮箱" prop="email">
              <el-input
                  v-model="orderCreateForm.queryEmail"
                  placeholder="请输入收货邮箱"
                  :disabled="itemInfo.stock === 0"
              />
            </el-form-item>

            <el-form-item label="查询密码" prop="queryPassword">
              <el-input
                  v-model="orderCreateForm.queryPassword"
                  placeholder="用于查询订单，请妥善保管"
                  show-password
                  :disabled="itemInfo.stock === 0"
              />
            </el-form-item>

            <el-form-item label="图形验证码" prop="captchaCode">
              <div class="captcha-row">
                <el-input
                    v-model="orderCreateForm.captchaCode"
                    placeholder="请输入右侧验证码"
                    :disabled="itemInfo.stock === 0"
                />
                <el-image
                    class="captcha-img"
                    :src="'data:image/png;base64,' + captchaInfo.imageBase64"
                    alt="点击刷新验证码"
                    @click="refreshCaptcha"
                    fit="cover"
                />
              </div>
            </el-form-item>

            <div class="pay-row">
              <el-button
                  class="order-btn"
                  type="primary"
                  size="large"
                  :disabled="itemInfo.stock === 0"
                  @click="onBuyClick"
              >
                下单
              </el-button>
            </div>
          </el-form>
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
        v-model:visible="orderDialogVisible"
        :order-info="orderInfo"
        @pay="onPayOrderClick"
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

.info-col {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.item-name {
  font-size: 26px;
  font-weight: 600;
}

.price-row {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.price {
  font-size: 26px;
  font-weight: 700;
  color: #ef4444;
}

.stock-line {
  font-size: 15px;
  font-weight: 500;
  color: #10b981;
}

.pay-row {
  margin-top: 4px;
}

.order-btn {
  font-size: 16px;
  padding: 8px 22px;
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

.captcha-row {
  display: flex;
  align-items: center;
  gap: 8px;
}

.captcha-img {
  width: 110px;
  height: 36px;
  cursor: pointer;
  border-radius: 4px;
  border: 1px solid #e5e7eb;
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
