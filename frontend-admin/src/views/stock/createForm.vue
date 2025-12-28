<script setup lang="ts">
import { ref } from "vue";
import { StockFormProps } from "./utils/types";
import { UploadUserFile } from "element-plus";

const props = withDefaults(defineProps<StockFormProps>(), {
  formInline: () => ({
    itemId: 0,
    content: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
const mode = ref("single");
const fileList = ref<UploadUserFile[]>([]);

function getRef() {
  return ruleFormRef.value;
}

function getMode() {
  return mode.value;
}

function getFile() {
  return fileList.value.length > 0 ? fileList.value[0].raw : null;
}

defineExpose({ getRef, getMode, getFile });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    label-width="82px"
  >
    <el-form-item label="添加方式">
      <el-radio-group v-model="mode">
        <el-radio-button label="single">单项添加</el-radio-button>
        <el-radio-button label="batch">批量上传</el-radio-button>
      </el-radio-group>
    </el-form-item>

    <template v-if="mode === 'single'">
      <el-form-item label="库存内容" prop="content"
        :rules="[{ required: true, message: '请输入库存内容', trigger: 'blur' }]"
      >
        <el-input
          v-model="newFormInline.content"
          type="textarea"
          :rows="10"
          placeholder="请输入库存内容"
        />
      </el-form-item>
    </template>

    <template v-else>
      <el-form-item label="上传文件" required>
        <el-upload
          v-model:file-list="fileList"
          class="upload-demo"
          action="#"
          :auto-upload="false"
          :limit="1"
          accept=".txt"
        >
          <el-button type="primary">选择文件</el-button>
          <template #tip>
            <div class="el-upload__tip">
              请上传txt文件，使用"====="作为分隔符
            </div>
          </template>
        </el-upload>
      </el-form-item>
    </template>
  </el-form>
</template>

