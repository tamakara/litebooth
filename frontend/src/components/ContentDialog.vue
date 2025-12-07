<script setup lang="ts">
import { computed } from 'vue'
import { ElMessage } from 'element-plus'

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

const handleCopy = async (text: string) => {
  if (!text) return

  try {
    if (navigator && navigator.clipboard && navigator.clipboard.writeText) {
      await navigator.clipboard.writeText(text)
    } else {
      const textarea = document.createElement('textarea')
      textarea.value = text
      textarea.style.position = 'fixed'
      textarea.style.top = '0'
      textarea.style.left = '0'
      textarea.style.opacity = '0'
      document.body.appendChild(textarea)
      textarea.focus()
      textarea.select()
      document.execCommand('copy')
      document.body.removeChild(textarea)
    }
    ElMessage.success('复制成功')
  } catch (e) {
    console.error('Copy failed', e)
    ElMessage.error('复制失败，请手动复制')
  }
}
</script>

<template>
  <el-dialog v-model="dialogVisible" title="卡密内容" width="500px">
    <div v-if="contentList && contentList.length" class="content-list">
      <div
        v-for="(content, index) in contentList"
        :key="content + index"
        class="content-row"
      >
        <div class="content-header">
          <span class="content-index">#{{ index + 1 }}</span>
          <el-button
            class="copy-btn"
            type="primary"
            link
            size="small"
            @click="handleCopy(content)"
          >
            复制
          </el-button>
        </div>
        <pre class="card-content">{{ content }}</pre>
      </div>
    </div>
    <div v-else class="empty-text">暂无卡密内容</div>
    <template #footer>
      <el-button type="primary" @click="dialogVisible = false">
        关闭
      </el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.content-list {
  max-height: 320px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.content-row {
  display: flex;
  flex-direction: column;

}

.content-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 13px;
  color: #6b7280;
}

.content-index {
  font-weight: 500;
}

.card-content {
  background: #f9fafb;
  padding: 12px;
  border-radius: 4px;
  white-space: pre-wrap;
  word-break: break-all;
}

.copy-btn {
  padding: 0 4px;
}

.empty-text {
  text-align: center;
  color: #9ca3af;
}
</style>
