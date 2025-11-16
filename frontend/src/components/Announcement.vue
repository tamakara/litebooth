<script setup>

</script>

<template>
  <div class="announcement">
    <div class="anno-inner">
      <h4 class="anno-title">公告</h4>
      <div class="anno-text">
        <slot/>
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


</style>
