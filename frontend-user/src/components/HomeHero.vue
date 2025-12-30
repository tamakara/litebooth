<script setup lang="ts">
defineProps({
  title: {
    type: String,
    default: ''
  },
  subtitle: {
    type: String,
    default: ''
  }
})
</script>

<template>
  <section class="hero">
    <h1 v-if="title">{{ title }}</h1>
    <p v-if="subtitle">{{ subtitle }}</p>
  </section>
</template>

<style scoped>
.hero {
  text-align: center;
  margin-bottom: 32px;
  padding: 40px 0;
}

.hero h1 {
  margin: 0 0 12px;
  font-size: 36px;
  font-weight: 800;
  color: var(--color-text-main);
  line-height: 1.2;
  letter-spacing: -0.025em;
}

.hero p {
  margin: 0 auto;
  max-width: 600px;
  color: var(--color-text-secondary);
  font-size: 18px;
  line-height: 1.6;
}
</style>

