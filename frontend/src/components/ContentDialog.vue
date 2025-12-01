<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  visible: boolean
  contentList?: string[]
}>()

const emit = defineEmits<{
  (e: 'update:visible', value: boolean): void
}>()

const dialogVisible = computed({
  get: () => props.visible,
  set: (value: boolean) => emit('update:visible', value)
})
</script>

<template>
  <el-dialog v-model="dialogVisible" title="卡密内容" width="500px">
    <pre v-for="content in contentList" :key="content" class="card-content">{{ content }}</pre>
    <template #footer>
      <el-button type="primary" @click="dialogVisible = false">
        关闭
      </el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.card-content {
  max-height: 320px;
  overflow: auto;
  background: #f9fafb;
  padding: 12px;
  border-radius: 4px;
  white-space: pre-wrap;
  word-break: break-all;
}
</style>

