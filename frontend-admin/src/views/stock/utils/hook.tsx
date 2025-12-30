import {reactive, ref, h, toRaw, onMounted} from "vue";
import {message} from "@/utils/message";
import {getItemList, type ItemPageQueryFormDTO, type ItemVO} from "@/api/item";
import {getStockList, createStock, batchCreateStock, deleteStock, updateStock, type StockCreateDTO, type StockUpdateDTO, type StockVO} from "@/api/stock";
import {addDialog} from "@/components/ReDialog";
import {PaginationProps} from "@pureadmin/table";
import EditCreateForm from "../createForm.vue";
import StockEditForm from "../editForm.vue";
import {type GroupListVO, getGroupListVO} from "@/api/group";

export function useStock() {
  const form = reactive<ItemPageQueryFormDTO>({
    keyword: "",
    groupId: 0,
    pageNum: 1,
    pageSize: 20
  });

  const formRef = ref();
  const dataList = ref<ItemVO[]>([]);
  const loading = ref(true);
  const groupList = ref<GroupListVO>([]);
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 20,
    currentPage: 1,
    background: true,
    pageSizes: [10, 20, 50, 100]
  });

  const stockMap = reactive<Record<number, StockVO[]>>({});
  const stockLoadingMap = reactive<Record<number, boolean>>({});

  const columns: TableColumnList = [
    {
      type: "expand",
      slot: "expand"
    },
    {
      label: "ID",
      prop: "id",
      width: 80
    },
    {
      label: "商品名称",
      prop: "name",
      minWidth: 150
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];

  async function onSearch() {
    loading.value = true;
    try {
      const res: any = await getItemList(toRaw(form));
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

  async function getGroupList() {
    try {
      const res: any = await getGroupListVO();
      if (res.code === 200) {
        groupList.value = res.data;
      }
    } catch (e) {
      console.error(e);
    }
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  async function loadStocks(itemId: number) {
    stockLoadingMap[itemId] = true;
    try {
      const res: any = await getStockList(itemId);
      if (res.code === 200) {
        stockMap[itemId] = res.data;
      }
    } catch (e) {
      console.error(e);
    } finally {
      stockLoadingMap[itemId] = false;
    }
  }

  function handleExpandChange(row: ItemVO, expandedRows: ItemVO[]) {
    const isExpanded = expandedRows.some(r => r.id === row.id);
    if (isExpanded) {
      loadStocks(Number(row.id));
    }
  }

  function openAddStockDialog(row: ItemVO) {
    addDialog({
      title: `添加库存 - ${row.name}`,
      props: {
        formInline: {
          itemId: Number(row.id),
          content: ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(EditCreateForm as any, {ref: formRef}),
      beforeSure: (done, {options}) => {
        const FormRef = formRef.value.getRef();
        const mode = formRef.value.getMode();

        if (mode === "single") {
          const curData = options.props.formInline as StockCreateDTO;
          FormRef.validate(valid => {
            if (valid) {
              createStock(curData).then((res: any) => {
                if (res.code === 200) {
                  message("添加成功", {type: "success"});
                  done();
                  loadStocks(Number(row.id));
                } else {
                  message(res.msg || "添加失败", {type: "error"});
                }
              });
            }
          });
        } else {
          const file = formRef.value.getFile();
          if (!file) {
            message("请选择文件", {type: "warning"});
            return;
          }
          batchCreateStock({itemId: Number(row.id), file}).then((res: any) => {
            if (res.code === 200) {
              message("批量添加成功", {type: "success"});
              done();
              loadStocks(Number(row.id));
            } else {
              message(res.msg || "批量添加失败", {type: "error"});
            }
          });
        }
      }
    });
  }

  function openEditStockDialog(row: StockVO) {
    addDialog({
      title: `修改库存`,
      props: {
        formInline: {
          id: row.id,
          orderId: row.orderId,
          content: row.content,
          isSold: row.isSold
        }
      },
      width: "40%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(StockEditForm as any, {ref: formRef}),
      beforeSure: (done, {options}) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as StockUpdateDTO;
        FormRef.validate(valid => {
          if (valid) {
            updateStock(curData).then(() => {
              message("修改成功", {type: "success"});
              done();
              loadStocks(row.itemId);
            });
          }
        });
      }
    });
  }

  function handleDeleteStock(stockId: number, itemId: number) {
    deleteStock(stockId).then(() => {
      message("删除成功", {type: "success"});
      loadStocks(itemId);
    });
  }

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
    getGroupList();
  });

  return {
    form,
    formRef,
    loading,
    columns,
    dataList,
    groupList,
    pagination,
    stockMap,
    stockLoadingMap,
    onSearch,
    resetForm,
    handleExpandChange,
    openAddStockDialog,
    openEditStockDialog,
    handleDeleteStock,
    handleSizeChange,
    handleCurrentChange
  };
}
