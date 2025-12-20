import { http } from "@/utils/http";
import type { ShopInfoUpdateFormDTO, ShopInfoUpdateFormVO } from "@/views/shop/utils/types";

/** 上传店铺Logo，返回文件ID字符串 */
export const uploadShopLogo = (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  return http.request<string>("post", "/file/uploadFile", {
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};

/** 获取当前店铺信息表单数据 */
export const getShopInfoUpdateForm = () => {
  return http.request<ShopInfoUpdateFormVO>("get", "/shop/vo/ShopInfoUpdateFormVO");
};

/** 更新店铺信息 */
export const updateShopInfo = (data: ShopInfoUpdateFormDTO) => {
  return http.request<boolean>("put", "/shop/updateShopInfo", { data });
};
