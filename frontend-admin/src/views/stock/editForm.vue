<script setup lang="ts">
import { ref } from "vue";
import { StockUpdateDTO } from "@/api/stock";

interface FormProps {
  formInline: StockUpdateDTO;
}

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    orderId: undefined,
    content: "",
    isSold: false
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

function getRef() {
  return ruleFormRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    label-width="82px"
  >
    <el-form-item label="订单ID" prop="orderId">
      <el-input-number
        v-model="newFormInline.orderId"
        placeholder="请输入订单ID"
        class="w-full"
        :controls="false"
      />
    </el-form-item>
    <el-form-item label="状态" prop="isSold">
      <el-switch
        v-model="newFormInline.isSold"
        active-text="已售出"
        inactive-text="未售出"
      />
    </el-form-item>
    <el-form-item label="库存内容" prop="content"
      :rules="[{ required: true, message: '请输入库存内容', trigger: 'blur' }]"
    >
      <el-input
        v-model="newFormInline.content"
        type="textarea"
        :rows="4"
        placeholder="请输入库存内容"
      />
    </el-form-item>
  </el-form>
</template>

