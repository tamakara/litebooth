import {reactive, ref, h, toRaw, onMounted} from "vue";
import {message} from "@/utils/message";
import {getItemList} from "@/api/item";
import {getStockList, createStock, batchCreateStock, deleteStock, updateStock, StockCreateDTO, StockUpdateDTO, StockVO} from "@/api/stock";
import {addDialog} from "@/components/ReDialog";
import {PaginationProps} from "@pureadmin/table";
import {ItemPageQueryFormDTO, ItemVO} from "@/views/item/utils/types";
import EditCreateForm from "../createForm.vue";
import StockEditForm from "../editForm.vue";
import {GroupListVO} from "@/views/group/utils/types";
import {getGroupListVO} from "@/api/group";

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
      const data = await getItemList(toRaw(form));
      dataList.value = data.records;
      pagination.total = data.total;
    } catch (e) {
      console.error(e);
    } finally {
      loading.value = false;
    }
  }

  async function getGroupList() {
    try {
      groupList.value = await getGroupListVO();
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
      stockMap[itemId] = await getStockList(itemId);
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
              createStock(curData).then(() => {
                message("添加成功", {type: "success"});
                done();
                loadStocks(Number(row.id));
              });
            }
          });
        } else {
          const file = formRef.value.getFile();
          if (!file) {
            message("请选择文件", {type: "warning"});
            return;
          }
          batchCreateStock({itemId: Number(row.id), file}).then(() => {
            message("批量添加成功", {type: "success"});
            done();
            loadStocks(Number(row.id));
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
