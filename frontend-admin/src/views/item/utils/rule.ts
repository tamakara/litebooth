import { reactive } from "vue";
import type { FormRules } from "element-plus";

export const formRules = reactive<FormRules>({
  name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
  price: [{ required: true, message: "请输入商品价格", trigger: "blur" }],
  groupId: [{ required: true, message: "请选择商品组", trigger: "change" }],
  coverFileId: [{ required: true, message: "请上传商品主图", trigger: "change" }],
  description: [{ required: true, message: "请输入商品描述", trigger: "blur" }]
});

