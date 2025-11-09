<script setup>
const props = defineProps({
  title: {type: String, default: '公告'}
})
</script>

<template>
  <div class="announcement">
    <div class="anno-inner">
      <h4 class="anno-title">{{ props.title }}</h4>
      <div class="anno-slot">
        <slot>
          <p class="anno-text">
            <span class="emoji">🤖</span>
            <strong>24小时自助下单</strong>
            <span> - 有任何问题可右下角</span>
            <span class="anno-highlight">在线咨询</span>
            <span>或者添加</span>
            <span class="anno-highlight">售后客服</span>
            <span>！</span>
          </p>
        </slot>
      </div>
    </div>
  </div>
</template>

<style scoped>
.announcement {
  background: #fff;
  border: 2px solid #e5e7eb;
  border-radius: 8px;
  padding: 20px 24px;
  margin: 16px 0;
  width: 100%;
  box-sizing: border-box;
}

.anno-inner {
  max-width: 100%;
}

.anno-title {
  margin: 0 0 8px;
  font-size: 20px;
  font-weight: 800;
  color: #111827;
}

.anno-text {
  margin: 0;
  font-size: 16px;
  line-height: 1.75;
  color: #343a40;
}

.anno-text .emoji {
  margin-right: 6px;
}

.anno-highlight {
  color: #e03e2d;
  font-weight: 700;
}
</style>
