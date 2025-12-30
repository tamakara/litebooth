<script setup lang="ts">
import { ref, onMounted } from "vue";
import ReCol from "@/components/ReCol";
import { useDark } from "./utils";
import WelcomeTable from "./components/table/index.vue";
import { ChartBar, ChartLine } from "./components/charts";
import Segmented, { type OptionsType } from "@/components/ReSegmented";
import { getDashboardData, DashboardVO } from "@/api/home";
import GroupLine from "~icons/ri/group-line";
import Question from "~icons/ri/question-answer-line";
import CheckLine from "~icons/ri/chat-check-line";
import Smile from "~icons/ri/star-smile-line";

defineOptions({
  name: "Home"
});

interface ChartDataItem {
  icon: any;
  bgColor: string;
  color: string;
  duration: number;
  name: string;
  value: number;
  valueStr?: string;
  percent: string;
  data: number[];
}

const { isDark } = useDark();

let curWeek = ref(1); // 0上周、1本周
const optionsBasis: Array<OptionsType> = [
  {
    label: "上周"
  },
  {
    label: "本周"
  }
];

const dashboardData = ref<DashboardVO>();
const chartData = ref<ChartDataItem[]>([]);
const barChartData = ref([]);
const progressData = ref([]);
const recentOrders = ref([]);

onMounted(async () => {
  try {
    const res: any = await getDashboardData();
    if (res.code === 200) {
      const data = res.data;
      dashboardData.value = data;

      // Update Cards
      chartData.value = [
        {
          icon: GroupLine,
          bgColor: "#effaff",
          color: "#41b6ff",
          duration: 2200,
          name: data.totalSales.name,
          value: parseFloat(data.totalSales.value.replace(/[^\d.]/g, '')),
          valueStr: data.totalSales.value,
          percent: data.totalSales.percent,
          data: data.totalSales.data
        },
        {
          icon: Question,
          bgColor: "#fff5f4",
          color: "#e85f33",
          duration: 1600,
          name: data.totalOrders.name,
          value: parseFloat(data.totalOrders.value),
          valueStr: data.totalOrders.value,
          percent: data.totalOrders.percent,
          data: data.totalOrders.data
        },
        {
          icon: CheckLine,
          bgColor: "#eff8f4",
          color: "#26ce83",
          duration: 1500,
          name: data.soldItems.name,
          value: parseFloat(data.soldItems.value),
          valueStr: data.soldItems.value,
          percent: data.soldItems.percent,
          data: data.soldItems.data
        },
        {
          icon: Smile,
          bgColor: "#f6f4fe",
          color: "#7846e5",
          duration: 100,
          name: data.totalStock.name,
          value: parseFloat(data.totalStock.value),
          valueStr: data.totalStock.value,
          percent: data.totalStock.percent,
          data: data.totalStock.data
        }
      ];

      // Update Bar Chart
      barChartData.value = [
        {
          data: data.salesTrend.lastWeekData,
          dates: data.salesTrend.dates
        },
        {
          data: data.salesTrend.thisWeekData,
          dates: data.salesTrend.dates
        }
      ];

      // Update Top Items
      progressData.value = data.topItems.map((item, index) => ({
        week: item.name,
        percentage: item.percentage,
        duration: 100,
        color: index % 2 === 0 ? "#41b6ff" : "#26ce83"
      }));

      // Update Recent Orders
      recentOrders.value = data.recentOrders;
    }
  } catch (e) {
    console.error(e);
  }
});
</script>

<template>
  <div>
    <el-row :gutter="24" justify="space-around">
      <re-col
        v-for="(item, index) in chartData"
        :key="index"
        v-motion
        class="mb-[18px]"
        :value="6"
        :md="12"
        :sm="12"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 80 * (index + 1)
          }
        }"
      >
        <el-card class="line-card" shadow="never">
          <div class="flex justify-between">
            <span class="text-md font-medium">
              {{ item.name }}
            </span>
            <div
              class="w-8 h-8 flex justify-center items-center rounded-md"
              :style="{
                backgroundColor: isDark ? 'transparent' : item.bgColor
              }"
            >
              <IconifyIconOffline
                :icon="item.icon"
                :color="item.color"
                width="18"
                height="18"
              />
            </div>
          </div>
          <div class="flex justify-between items-start mt-3">
            <div class="w-1/2">
              <span class="text-2xl font-medium">{{ item.valueStr || item.value }}</span>
              <p v-if="item.percent" class="font-medium text-green-500">{{ item.percent }}</p>
            </div>
            <ChartLine
              v-if="item.data && item.data.length > 1"
              class="w-1/2!"
              :color="item.color"
              :data="item.data"
            />
          </div>
        </el-card>
      </re-col>

      <re-col
        v-motion
        class="mb-[18px]"
        :value="18"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 400
          }
        }"
      >
        <el-card class="bar-card" shadow="never">
          <div class="flex justify-between">
            <span class="text-md font-medium">销售趋势</span>
            <Segmented v-model="curWeek" :options="optionsBasis" />
          </div>
          <div class="flex justify-between items-start mt-3">
            <ChartBar
              v-if="barChartData.length > 0"
              :data="barChartData[curWeek].data"
              :xAxisData="barChartData[curWeek].dates"
            />
          </div>
        </el-card>
      </re-col>

      <re-col
        v-motion
        class="mb-[18px]"
        :value="6"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 480
          }
        }"
      >
        <el-card shadow="never">
          <div class="flex justify-between">
            <span class="text-md font-medium">热销商品</span>
          </div>
          <div
            v-for="(item, index) in progressData"
            :key="index"
            :class="[
              'flex',
              'justify-between',
              'items-start',
              index === 0 ? 'mt-8' : 'mt-[2.15rem]'
            ]"
          >
            <el-progress
              :text-inside="true"
              :percentage="item.percentage"
              :stroke-width="21"
              :color="item.color"
              striped
              striped-flow
              :duration="item.duration"
            />
            <span class="text-nowrap ml-2 text-text_color_regular text-sm">
              {{ item.week }}
            </span>
          </div>
        </el-card>
      </re-col>

      <re-col
        v-motion
        class="mb-[18px]"
        :value="24"
        :xs="24"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 560
          }
        }"
      >
        <el-card shadow="never" class="h-[580px]">
          <div class="flex justify-between">
            <span class="text-md font-medium">最新订单</span>
          </div>
          <WelcomeTable class="mt-3" :data="recentOrders" />
        </el-card>
      </re-col>
    </el-row>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-card) {
  --el-card-border-color: none;

  /* 解决概率进度条宽度 */
  .el-progress--line {
    width: 85%;
  }

  /* 解决概率进度条字体大小 */
  .el-progress-bar__innerText {
    font-size: 15px;
  }

  /* 隐藏 el-scrollbar 滚动条 */
  .el-scrollbar__bar {
    display: none;
  }

  /* el-timeline 每一项上下、左右边距 */
  .el-timeline-item {
    margin: 0 6px;
  }
}

.main-content {
  margin: 20px 20px 0 !important;
}
</style>
