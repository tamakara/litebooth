import { reactive, ref, toRaw, onMounted } from "vue";
import { getOrderPageVO, type OrderVO, type OrderPageQueryDTO } from "@/api/order";
import { PaginationProps } from "@pureadmin/table";

export function useOrder() {
  const form = reactive<OrderPageQueryDTO>({
    keyword: "",
    status: undefined,
    pageNum: 1,
    pageSize: 20
  });

  const dataList = ref<OrderVO[]>([]);
  const loading = ref(true);
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 20,
    currentPage: 1,
    background: true,
    pageSizes: [10, 20, 50, 100]
  });

  const columns: TableColumnList = [
    {
      label: "ID",
      prop: "id",
      width: 180
    },
    {
      label: "商品名称",
      prop: "itemName",
      minWidth: 150
    },
    {
      label: "查询邮箱",
      prop: "queryEmail",
      minWidth: 150
    },
    {
      label: "金额",
      prop: "amount",
      width: 100
    },
    {
      label: "状态",
      prop: "status",
      width: 100,
      slot: "status"
    },
    {
      label: "支付方式",
      prop: "paymentMethod",
      width: 100,
      slot: "paymentMethod"
    },
    {
      label: "创建时间",
      prop: "createdAt",
      width: 180
    },
    {
      label: "支付时间",
      prop: "paymentAt",
      width: 180
    }
  ];

  async function onSearch() {
    loading.value = true;
    try {
      const res: any = await getOrderPageVO(toRaw(form));
      if (res.code === 200) {
        const data = res.data;
        dataList.value = data.records;
        pagination.total = data.total;
      }
    } catch (e) {
      console.error(e);
    } finally {
      loading.value = false;
    }
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  function handleSizeChange(val: number) {
    form.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    form.pageNum = val;
    onSearch();
  }

  onMounted(() => {
    onSearch();
  });

  return {
    form,
    loading,
    columns,
    dataList,
    pagination,
    onSearch,
    resetForm,
    handleSizeChange,
    handleCurrentChange
  };
}

