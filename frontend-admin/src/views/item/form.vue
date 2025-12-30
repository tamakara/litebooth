<script setup lang="ts">
import {ref} from "vue";
import {formRules} from "./utils/rule";
import {uploadFile} from "@/api/file";
import {message} from "@/utils/message";
import {Plus} from "@element-plus/icons-vue";
import {ItemProps} from "@/views/item/utils/types";

const props = defineProps<ItemProps>();

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
const coverPreviewUrl = ref(props.coverFileUrl);

function getRef() {
  return ruleFormRef.value;
}

const customUpload = async (options) => {
  try {
    const res: any = await uploadFile(options.file);
    if (res.code === 200) {
      const fileId = res.data;
      newFormInline.value.coverFileId = Number(fileId);
      coverPreviewUrl.value = URL.createObjectURL(options.file);
      message("上传成功", {type: "success"});
    } else {
      message(res.msg || "上传失败", {type: "error"});
    }
  } catch (error) {
    message("上传失败", {type: "error"});
  }
};

defineExpose({getRef});
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

    <el-form-item label="商品组" prop="groupId">
      <el-select v-model="newFormInline.groupId" placeholder="请选择商品组" clearable>
        <template v-for="g in groupList" :key="g.id">
          <el-option
            v-if="g.id!==0"
            :label="g.name"
            :value="g.id"
          />
        </template>
      </el-select>
    </el-form-item>

    <el-form-item label="商品主图" prop="coverFileId">
      <el-upload
        class="avatar-uploader"
        action="#"
        :http-request="customUpload"
        :show-file-list="false"
      >
        <el-image v-if="coverPreviewUrl" :src="coverPreviewUrl" class="avatar"
                  style="width: 100px; height: 100px; object-fit: cover; border-radius: 4px; border: 1px solid #dcdfe6;"/>
        <el-icon v-else class="avatar-uploader-icon"
                 style="border: 1px dashed #d9d9d9; border-radius: 6px; cursor: pointer; position: relative; overflow: hidden; width: 100px; height: 100px; display: flex; justify-content: center; align-items: center; font-size: 28px; color: #8c939d;">
          <Plus/>
        </el-icon>
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
      <el-switch v-model="newFormInline.isActive" active-text="上架" inactive-text="下架"/>
    </el-form-item>
  </el-form>
</template>

