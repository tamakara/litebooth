import editForm from "../form.vue";
import {message} from "@/utils/message";
import {getGroupList, createGroup, updateGroup, deleteGroup} from "@/api/group";
import {addDialog} from "@/components/ReDialog";
import {reactive, ref, onMounted, h} from "vue";
import {PaginationProps} from "@pureadmin/table";
import type {FormItemProps, GroupPageQueryFormDTO} from "../utils/types";

export function useGroup() {
  const form = reactive<GroupPageQueryFormDTO>({
    name: "",
    pageNum: 1,
    pageSize: 20
  });

  const formRef = ref();
  const dataList = ref([]);
  const loading = ref(true);
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 20,
    currentPage: 1,
    background: true,
    pageSizes:[10,20,50,100],
  });

  const columns: TableColumnList = [
    {
      label: "ID",
      prop: "id",
      width: 80
    },
    {
      label: "商品组名称",
      prop: "name",
      minWidth: 180
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];

  function handleSelectionChange(val) {
    console.log("handleSelectionChange", val);
  }

  function resetForm(formEl) {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  }

  async function onSearch() {
    loading.value = true;
    try {
      const data = await getGroupList(form);
      dataList.value = data.records;
      pagination.total = data.total;
      pagination.pageSize = data.pageSize;
      pagination.currentPage = data.pageNum;
    } catch (e) {
      console.error(e);
      message("获取商品组列表失败", {type: "error"});
      dataList.value = [];
    } finally {
      loading.value = false;
    }
  }

  function handleSizeChange(val: number) {
    form.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    form.pageNum = val;
    onSearch();
  }

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}商品组`,
      props: {
        formInline: {
          id: row?.id,
          name: row?.name ?? ""
        }
      },
      width: "30%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, {ref: formRef} as any),
      beforeSure: (done, {options}) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        FormRef.validate(valid => {
          if (valid) {
            if (title === "新增") {
              createGroup(curData.name)
                .then(() => {
                  message(`您新增了商品组名称为${curData.name}的这条数据`, {
                    type: "success"
                  });
                  done(); // 关闭弹框
                  onSearch(); // 刷新表格数据
                })
                .catch(() => {
                  message("新增商品组失败", {type: "error"});
                });
            } else {
              updateGroup(curData as any)
                .then(() => {
                  message(`您修改了商品组名称为${curData.name}的这条数据`, {
                    type: "success"
                  });
                  done(); // 关闭弹框
                  onSearch(); // 刷新表格数据
                })
                .catch(() => {
                  message("修改商品组失败", {type: "error"});
                });
            }
          }
        });
      }
    } as any);
  }

  function handleDelete(row) {
    deleteGroup(row.id)
      .then(() => {
        message(`您删除了商品组名称为${row.name}的这条数据`, {
          type: "success"
        });
        onSearch();
      })
      .catch(() => {
        message("删除商品组失败", {type: "error"});
      });
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
    openDialog,
    handleDelete,
    handleSelectionChange,
    handleSizeChange,
    handleCurrentChange
  };
}
