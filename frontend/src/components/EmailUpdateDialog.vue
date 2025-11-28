<script setup>
import { reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  oldEmail: {
    type: String,
    default: ''
  }
})

// 增加 send-code 事件，用于让父组件处理发送验证码
const emit = defineEmits(['update:visible', 'submit', 'send-code'])

const form = reactive({
  newEmail: '',
  code: ''
})

watch(
  () => props.visible,
  (val) => {
    if (val) {
      form.newEmail = ''
      form.code = ''
    }
  }
)

const handleClose = () => {
  emit('update:visible', false)
}

const handleSendCode = () => {
  emit('send-code', form.newEmail)
}

const handleSubmit = () => {
  if (!form.newEmail) {
    ElMessage.error('请输入新邮箱')
    return
  }
  if (!form.code) {
    ElMessage.error('请输入验证码')
    return
  }
  emit('submit', { ...form })
}
</script>

<template>
  <el-dialog :model-value="visible" title="修改邮箱" width="420px" @close="handleClose">
    <el-form label-width="90px">
      <el-form-item label="旧邮箱">
        <div class="plain-value">{{ oldEmail }}</div>
      </el-form-item>
      <el-form-item label="新邮箱">
        <el-input v-model="form.newEmail" placeholder="输入新邮箱" />
      </el-form-item>
      <el-form-item label="验证码">
        <el-input v-model="form.code" placeholder="输入验证码">
          <template #append>
            <el-button @click="handleSendCode">发送</el-button>
          </template>
        </el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.plain-value {
  color: #606266;
}
</style>
