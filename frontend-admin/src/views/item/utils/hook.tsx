import editForm from "../form.vue";
import {message} from "@/utils/message";
import {getItemList, createItem, updateItem, deleteItem} from "@/api/item";
import {addDialog} from "@/components/ReDialog";
import {reactive, ref, onMounted, h} from "vue";
import {PaginationProps} from "@pureadmin/table";
import {
  ItemCreateOrUpdateFormDTO,
  ItemPageQueryFormDTO,
  ItemVO
} from "../utils/types";
import {GroupListVO, GroupMapVO} from "@/views/group/utils/types";
import {getGroupListVO, getGroupMapVO} from "@/api/group";

export function useItem() {
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
  const groupMap = ref<GroupMapVO>({});
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
      width: 80
    },
    {
      label: "主图",
      prop: "cover",
      slot: "cover",
      width: 100
    },
    {
      label: "商品名称",
      prop: "name",
      minWidth: 150
    },
    {
      label: "价格",
      prop: "price",
      width: 100
    },
    {
      label: "商品组",
      prop: "groupName",
      width: 120
    },
    {
      label: "状态",
      prop: "isActive",
      slot: "isActive",
      width: 100
    },
    {
      label: "描述",
      prop: "description",
      minWidth: 200
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];

  async function getGroupList() {
    try {
      const data = await getGroupListVO();
      groupList.value = data;
    } catch (e) {
      console.error(e);
    }
  }

  async function getGroupMap() {
    try {
      const data = await getGroupMapVO();
      groupMap.value = data;
    } catch (e) {
      console.error(e);
    }
  }

  async function onSearch() {
    loading.value = true;
    try {
      const data = await getItemList(form);
      dataList.value = data.records;
      pagination.total = data.total;
      pagination.pageSize = data.pageSize;
      pagination.currentPage = data.pageNum;
    } catch (e) {
      console.error(e);
      message("获取商品列表失败", {type: "error"});
      dataList.value = [];
    } finally {
      loading.value = false;
    }
  }

  function resetForm(formEl) {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  }

  function handleSizeChange(val: number) {
    form.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    form.pageNum = val;
    onSearch();
  }

  function handleDelete(row) {
    deleteItem(row.id).then(() => {
      message("删除成功", {type: "success"});
      onSearch();
    });
  }

  function openDialog(title = "新增", row?: ItemCreateOrUpdateFormDTO) {
    addDialog({
      title: `${title}商品`,
      props: {
        formInline: {
          id: row?.id ?? 0,
          name: row?.name ?? "",
          price: row?.price ?? 0,
          groupId: row?.groupId ?? null,
          coverFileId: row?.coverFileId ?? null,
          description: row?.description ?? "",
          isActive: row?.isActive ?? false
        },
        groupList: groupList.value,
        coverFileUrl: row?.coverFileUrl ?? ""
      },
      width: "50%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, {ref: formRef} as any),
      beforeSure: (done, {options}) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline;
        FormRef.validate(valid => {
          if (valid) {
            if (title === "新增") {
              createItem(curData).then(() => {
                message("新增成功", {type: "success"});
                done();
                onSearch();
              });
            } else {
              updateItem(curData).then(() => {
                message("修改成功", {type: "success"});
                done();
                onSearch();
              });
            }
          }
        });
      }
    });
  }

  onMounted(() => {
    onSearch();
    getGroupList();
    getGroupMap();
  });

  return {
    form,
    loading,
    columns,
    dataList,
    pagination,
    groupList,
    groupMap,
    onSearch,
    resetForm,
    openDialog,
    handleDelete,
    handleSizeChange,
    handleCurrentChange
  };
}

