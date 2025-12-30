<script setup lang="ts">
import {PropType, ref} from 'vue'
import {ItemInfoVO, OrderCreateFormDTO} from "@/types";
import {CaptchaInfoVO} from "@/types/captcha";
import {FormInstance} from "element-plus";

const props = defineProps({
  itemInfo: {
    type: Object as PropType<ItemInfoVO>,
    required: true
  },
  modelValue: {
    type: Object as PropType<OrderCreateFormDTO>,
    required: true
  },
  captchaInfo: {
    type: Object as PropType<CaptchaInfoVO>,
    required: true
  },
  verifyCaptcha: {
    type: Function,
    required: true
  }
})

const emit = defineEmits(['update:modelValue', 'refreshCaptcha', 'submit', 'reset'])

const formRef = ref<FormInstance>()

const validateCaptcha = async (_rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('请输入图形验证码'))
  }
  try {
    const token = await props.verifyCaptcha(value)
    if (token) {
      props.modelValue.captchaToken = token
      callback()
    } else {
      callback(new Error('验证码错误'))
    }
  } catch (e) {
    callback(new Error('验证码校验失败'))
  }
}

const rules = {
  queryEmail: [
    {required: true, message: "请输入邮箱", trigger: ['blur', 'change']},
    {type: "email", message: "邮箱格式不正确", trigger: ["blur", "change"]}
  ],
  queryPassword: [
    {required: true, message: '请输入订单查询密码', trigger: 'blur'},
    {min: 4, message: '查询密码至少 4 位', trigger: 'blur'}
  ],
  captchaCode: [
    {required: true, validator: validateCaptcha, trigger: 'blur'}
  ],
}

const onSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate((valid) => {
    if (valid) {
      emit('submit')
    }
  })
}

const onReset = () => {
  if (!formRef.value) return
  formRef.value.resetFields()
  emit('reset')
}
</script>

<template>
  <el-form
      ref="formRef"
      :model="modelValue"
      :rules="rules"
      label-position="top"
      label-width="auto"
  >
    <div class="order-form-grid">
      <el-form-item label="收货邮箱" prop="queryEmail">
        <el-input
            v-model="modelValue.queryEmail"
            placeholder="请输入收货邮箱"
            :disabled="itemInfo.stock === 0"
        />
      </el-form-item>

      <el-form-item label="购买数量" prop="quantity">
        <el-input-number
            v-if="itemInfo.stock > 0"
            v-model="modelValue.quantity"
            :min="1"
            :max="itemInfo.stock"
        />
        <el-input-number
            v-else
            v-model="modelValue.quantity"
            :min="0"
            :max="0"
        />
      </el-form-item>

      <el-form-item label="查询密码" prop="queryPassword">
        <el-input
            v-model="modelValue.queryPassword"
            placeholder="用于查询订单，请妥善保管"
            show-password
            :disabled="itemInfo.stock === 0"
        />
      </el-form-item>

      <el-form-item label="验证码" prop="captchaCode">
        <div class="captcha-row">
          <el-input
              v-model="modelValue.captchaCode"
              placeholder="请输入右侧验证码"
              :disabled="itemInfo.stock === 0"
          />
          <el-image
              class="captcha-img"
              :src="'data:image/png;base64,' + captchaInfo.imageBase64"
              alt="点击刷新验证码"
              @click="$emit('refreshCaptcha')"
              fit="contain"
          />
        </div>
      </el-form-item>

      <el-form-item label="支付方式" prop="paymentMethod" class="order-form-pay-method">
        <el-radio-group v-model="modelValue.paymentMethod" :disabled="itemInfo.stock === 0">
          <el-radio label="WXPAY">微信</el-radio>
          <el-radio label="ALIPAY">支付宝</el-radio>
        </el-radio-group>
      </el-form-item>

      <div class="order-form-actions">
        <el-button
            class="order-btn"
            type="primary"
            size="large"
            :disabled="itemInfo.stock === 0"
            @click="onSubmit"
        >
          下单
        </el-button>
        <el-button
            class="order-btn"
            size="large"
            :disabled="itemInfo.stock === 0"
            @click="onReset"
        >
          重置
        </el-button>
      </div>
    </div>
  </el-form>
</template>

<style scoped>
.captcha-row {
  display: flex;
  align-items: center;
  gap: 8px;
}

.captcha-img {
  width: 200px;
  height: 36px;
  cursor: pointer;
  border-radius: 2px;
  border: 1px solid #e5e7eb;
}

.order-form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  column-gap: 16px;
  row-gap: 4px;
}

.order-form-grid :deep(.el-form-item__label) {
  margin-bottom: 2px;
  padding-bottom: 0;
}

.order-form-pay-method {
  grid-column: 1 / -1;
}

.order-form-actions {
  grid-column: 1 / -1;
  margin-top: 4px;
}

.order-btn {
  font-size: 16px;
  padding: 8px 22px;
}

@media (max-width: 768px) {
  .order-form-grid {
    grid-template-columns: 1fr;
  }
}
</style>

