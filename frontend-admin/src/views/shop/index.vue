<script setup lang="ts">
import { useShop } from "./utils/hook";
import { formRules } from "./utils/rule";
import { ElForm, ElFormItem, ElInput, ElButton, ElUpload, ElImage } from "element-plus";

defineOptions({
  name: "Shop"
});

const {
  form,
  formRef,
  loading,
  logoPreviewUrl,
  customUploadRequest,
  handleSubmit,
  handleReset
} = useShop();
</script>

<template>
  <el-card shadow="never" :body-style="{ height: 'calc(100vh - 260px)' }">
    <template #header>
      店铺管理
    </template>
    <div class="max-w-[640px] m-auto p-4">
      <el-form
        ref="formRef"
        :model="form"
        :rules="formRules"
        label-width="100px"
        label-position="right"
      >
        <!-- Logo上传 -->
        <el-form-item label="店铺Logo" prop="logoFileId">
          <el-upload
            class="avatar-uploader"
            :action="''"
            :show-file-list="false"
            :limit="1"
            :http-request="customUploadRequest"
            accept="image/*"
          >
            <el-image
              :src="logoPreviewUrl"
              style="width: 80px; height: 80px; border-radius: 50%; border: 1px dashed #d9d9d9; cursor: pointer; object-fit: cover;"
              fit="cover"
            />
          </el-upload>
          <span class="text-xs text-gray-500 leading-snug ml-3">
            点击头像更换 Logo，建议尺寸 200×200，支持 JPG/PNG
          </span>
        </el-form-item>

        <el-form-item label="店铺名称" prop="logoTitle">
          <el-input v-model="form.logoTitle" placeholder="请输入 Logo 标题" />
        </el-form-item>

        <el-form-item label="首页标题" prop="homeTitle">
          <el-input v-model="form.homeTitle" placeholder="请输入首页标题" />
        </el-form-item>

        <el-form-item label="首页副标题" prop="homeSubtitle">
          <el-input v-model="form.homeSubtitle" placeholder="请输入首页副标题" />
        </el-form-item>

        <el-form-item label="首页公告" prop="homeAnnouncement">
          <el-input
            v-model="form.homeAnnouncement"
            type="textarea"
            :rows="3"
            :autosize="{ minRows: 3, maxRows: 10 }"
            placeholder="请输入首页公告内容，支持HTML标签"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<style scoped>
</style>


