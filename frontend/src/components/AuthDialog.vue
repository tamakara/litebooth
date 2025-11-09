<script setup>
import {ref, reactive, watch} from 'vue'
import {useUserStore} from '../stores/userStore'
import { ElMessage } from 'element-plus'

const props = defineProps({visible: {type: Boolean, default: false}})
const emit = defineEmits(['update:visible', 'success'])
const user = useUserStore()
const isRegister = ref(false)
const loginForm = reactive({username: '', password: ''})
const registerForm = reactive({username: '', password: '', email: '', code: ''})

watch(() => props.visible, v => {
  if (!v) reset()
})

function reset() {
  loginForm.username = ''
  loginForm.password = ''
  registerForm.username = ''
  registerForm.password = ''
  registerForm.email = ''
  registerForm.code = ''
  user.error = ''
  isRegister.value = false
}

function sendEmailCode(scene) {
  ElMessage.success(`验证码已发送（${scene}）`)
}

async function submit() {
  if (isRegister.value) {
    const { username, password, email } = registerForm
    await user.register({ username, password, email })
  } else {
    await user.login(loginForm)
  }
  if (user.isLogin) {
    emit('success')
    emit('update:visible', false)
  }
}
</script>

<template>
  <el-dialog
      :model-value="props.visible"
      :title="isRegister ? '注册' : '登录'" width="420px"
      @close="emit('update:visible', false)"
  >
    <template v-if="!isRegister">
      <el-form label-width="72px" @submit.prevent>
        <el-form-item label="用户名">
          <el-input v-model="loginForm.username" autocomplete="off" placeholder="用户名"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginForm.password" type="password" show-password placeholder="密码"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="user.loading" @click="submit">登录</el-button>
          <el-button text @click="isRegister = true">没有账号? 注册</el-button>
        </el-form-item>
        <el-alert v-if="user.error" :title="user.error" type="error" show-icon/>
      </el-form>
    </template>

    <template v-else>
      <el-form label-width="72px" @submit.prevent>
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" autocomplete="off" placeholder="用户名"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="registerForm.email" type="text" placeholder="邮箱"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password" type="password" show-password placeholder="密码"/>
        </el-form-item>
        <el-form-item label="验证码">
          <el-input v-model="registerForm.code" placeholder="输入验证码">
            <template #append>
              <el-button @click="sendEmailCode('注册')">发送</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="user.loading" @click="submit">注册</el-button>
          <el-button text @click="isRegister = false">已有账号? 登录</el-button>
        </el-form-item>
        <el-alert v-if="user.error" :title="user.error" type="error" show-icon/>
      </el-form>
    </template>
  </el-dialog>
</template>

<style scoped>
</style>
