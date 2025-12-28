import { ref, reactive } from "vue";
import type { PaginationProps } from "@pureadmin/table";

export function useColumns() {
  const loading = ref(false);
  const columns: TableColumnList = [
    {
      label: "ID",
      prop: "id",
      width: 80
    },
    {
      label: "商品名称",
      prop: "itemName",
      minWidth: 150
    },
    {
      label: "金额",
      prop: "amount",
      width: 100,
      cellRenderer: ({ row }) => <span>¥{Number(row.amount).toFixed(2)}</span>
    },
    {
      label: "状态",
      prop: "status",
      width: 100,
      cellRenderer: ({ row }) => {
        const type = row.status === "FINISHED" ? "success" : row.status === "UNPAID" ? "warning" : "info";
        return (
          <el-tag type={type as any}>
            {row.status === "FINISHED"
              ? "已完成"
              : row.status === "UNPAID"
              ? "待付款"
              : "已过期"}
          </el-tag>
        );
      }
    },
    {
      label: "创建时间",
      prop: "createdAt",
      width: 180
    }
  ];

  /** 分页配置 */
  const pagination = reactive<PaginationProps>({
    pageSize: 5,
    currentPage: 1,
    layout: "prev, pager, next",
    total: 0,
    align: "center"
  });

  function onCurrentChange(page: number) {
    pagination.currentPage = page;
  }

  return {
    loading,
    columns,
    pagination,
    onCurrentChange
  };
}
