import { reactive } from "vue";
import type { FormRules } from "element-plus";

export const formRules = reactive<FormRules>({
  logo: [{ required: true, message: "请上传店铺 Logo", trigger: "change" }],
  logoTitle: [{ required: true, message: "请输入 Logo 标题", trigger: "blur" }],
  homeTitle: [{ required: false, message: "请输入首页标题", trigger: "blur" }],
  homeSubtitle: [
    { required: false, message: "请输入首页副标题", trigger: "blur" }
  ],
  homeAnnouncement: [
    { required: false, message: "请输入首页公告", trigger: "blur" }
  ]
});

