<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { ItemProps } from "./utils/types";
import { uploadShopLogo } from "@/api/shop";
import { message } from "@/utils/message";
import { Plus } from "@element-plus/icons-vue";

const props = defineProps<ItemProps>();

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
const coverPreviewUrl = ref("");

// Initialize preview if cover exists (assuming we can get URL or just show ID placeholder)
// Since we don't have the URL in formInline (only ID), we might need to fetch it or just rely on backend to send it in a different way.
// But for now, let's assume if we have an ID, we can construct a URL or just show it.
// Actually, for the edit form, we might want to show the existing image.
// If the backend sends `coverUrl` in the list, we can pass it to the form.
// But `ItemFormProps` only has `cover` (number).
// Let's assume we can construct the URL: /api/file/download/{id}
if (newFormInline.value.cover) {
    coverPreviewUrl.value = `/api/file/download/${newFormInline.value.cover}`;
}

function getRef() {
  return ruleFormRef.value;
}

const customUpload = async (options) => {
  try {
    const res = await uploadShopLogo(options.file);
    // res is string ID
    newFormInline.value.cover = Number(res);
    coverPreviewUrl.value = URL.createObjectURL(options.file);
    message("上传成功", { type: "success" });
  } catch (error) {
    message("上传失败", { type: "error" });
  }
};

defineExpose({ getRef });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    :rules="formRules"
    label-width="82px"
  >
    <el-form-item label="商品名称" prop="name">
      <el-input
        v-model="newFormInline.name"
        clearable
        placeholder="请输入商品名称"
      />
    </el-form-item>

    <el-form-item label="商品价格" prop="price">
      <el-input-number
        v-model="newFormInline.price"
        :min="0"
        :precision="2"
        controls-position="right"
      />
    </el-form-item>

    <el-form-item label="商品组" prop="group">
      <el-select v-model="newFormInline.group" placeholder="请选择商品组" clearable>
        <el-option
          v-for="item in groupOptions"
          :key="item.id"
          :label="item.name"
          :value="item.name"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="商品主图" prop="cover">
       <el-upload
          class="avatar-uploader"
          action="#"
          :http-request="customUpload"
          :show-file-list="false"
        >
          <img v-if="coverPreviewUrl" :src="coverPreviewUrl" class="avatar" style="width: 100px; height: 100px; object-fit: cover; border-radius: 4px; border: 1px solid #dcdfe6;" />
          <el-icon v-else class="avatar-uploader-icon" style="border: 1px dashed #d9d9d9; border-radius: 6px; cursor: pointer; position: relative; overflow: hidden; width: 100px; height: 100px; display: flex; justify-content: center; align-items: center; font-size: 28px; color: #8c939d;"><Plus /></el-icon>
        </el-upload>
    </el-form-item>

    <el-form-item label="商品描述" prop="description">
      <el-input
        v-model="newFormInline.description"
        type="textarea"
        placeholder="请输入商品描述"
      />
    </el-form-item>

    <el-form-item label="状态" prop="isActive">
      <el-switch v-model="newFormInline.isActive" active-text="上架" inactive-text="下架" />
    </el-form-item>
  </el-form>
</template>

