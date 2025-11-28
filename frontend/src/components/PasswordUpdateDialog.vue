<script setup>
import {reactive, watch} from 'vue'
import {ElMessage} from 'element-plus'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:visible', 'submit', 'send-code'])

const form = reactive({
  newPassword: '',
  confirmPassword: '',
  code: ''
})

watch(
    () => props.visible,
    (val) => {
      if (val) {
        form.newPassword = ''
        form.code = ''
        form.confirmPassword = ''
      }
    }
)

const handleClose = () => {
  emit('update:visible', false)
}

const handleSendCode = () => {
  emit('send-code')
}

const handleSubmit = () => {
  if (!form.newPassword || !form.confirmPassword) {
    ElMessage.error('请填写新密码和确认密码')
    return
  }
  if (form.newPassword !== form.confirmPassword) {
    ElMessage.error('两次输入的新密码不一致')
    return
  }
  if (!form.code) {
    ElMessage.error('请输入邮箱验证码')
    return
  }
  emit('submit', {newPassword: form.newPassword, code: form.code})
}
</script>

<template>
  <el-dialog :model-value="visible" title="修改密码" width="420px" @close="handleClose">
    <el-form label-width="90px">
      <el-form-item label="新密码">
        <el-input v-model="form.newPassword" type="password" placeholder="输入新密码"/>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.confirmPassword" type="password" placeholder="再次输入新密码"/>
      </el-form-item>
      <el-form-item label="验证码">
        <el-input v-model="form.code" placeholder="邮箱验证码">
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
</style>
