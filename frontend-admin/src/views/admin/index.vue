<script setup lang="ts">
import { reactive, ref } from "vue";
import { message } from "@/utils/message";
import { changePassword, ChangePasswordDTO } from "@/api/user";
import type { FormInstance, FormRules } from "element-plus";

defineOptions({
  name: "Admin"
});

const ruleFormRef = ref<FormInstance>();

const form = reactive<ChangePasswordDTO>({
  oldPassword: "",
  newPassword: ""
});

const rules = reactive<FormRules>({
  oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
  newPassword: [{ required: true, message: "请输入新密码", trigger: "blur" }]
});

const onSubmit = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      try {
        await changePassword(form);
        message("密码修改成功", { type: "success" });
        form.oldPassword = "";
        form.newPassword = "";
      } catch (error) {
        console.error(error);
      }
    }
  });
};
</script>

<template>
  <el-card class="box-card m-4" shadow="never">
    <template #header>
      <div class="card-header">
        <span>修改密码</span>
      </div>
    </template>
    <el-form
      ref="ruleFormRef"
      :model="form"
      :rules="rules"
      label-width="100px"
      class="demo-ruleForm max-w-[460px]"
    >
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="form.oldPassword" type="password" show-password />
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="form.newPassword" type="password" show-password />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit(ruleFormRef)">
          确认修改
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
