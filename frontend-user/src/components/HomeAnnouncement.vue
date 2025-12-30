<script setup lang="ts">
defineProps({
  announcement: {
    type: String,
    default: ''
  }
})
</script>

<template>
  <section class="announcement" v-if="announcement">
    <h4 class="anno-title">公告</h4>
    <div class="anno-text" v-html="announcement"/>
  </section>
</template>

<style scoped>
.announcement {
  background: #fff;
  border: 2px solid #e5e7eb;
  border-radius: 8px;
  padding: 24px 24px;
  margin: 16px 0;
  width: 100%;
  box-sizing: border-box;
}

.anno-title {
  margin: 8px 0;
  font-size: 20px;
  font-weight: 800;
  color: #111827;
}

.anno-text {
  font-size: 16px;
  line-height: 1.75;
  color: #343a40;
}
</style>

