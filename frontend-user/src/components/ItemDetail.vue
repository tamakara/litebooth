<script setup lang="ts">
import {PropType} from 'vue'
import {ItemInfoVO} from "@/types";

defineProps({
  itemInfo: {
    type: Object as PropType<ItemInfoVO>,
    required: true
  }
})
</script>

<template>
  <div class="info-col">
    <div class="item-name">{{ itemInfo.name }}</div>

    <div class="price-row">
      <div class="price">¥ {{ Number(itemInfo.price).toFixed(2) }}</div>
      <div class="stock-line">
        <span v-if="itemInfo.stock > 0">库存: {{ itemInfo.stock }}</span>
        <span v-else style="color: red; font-weight: bold;">商品缺货</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.info-col {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.item-name {
  font-size: 30px;
  font-weight: 700;
}

.price-row {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.price {
  font-size: 30px;
  font-weight: 800;
  color: #ef4444;
}

.stock-line {
  font-size: 18px;
  font-weight: 600;
  color: #10b981;
}
</style>

