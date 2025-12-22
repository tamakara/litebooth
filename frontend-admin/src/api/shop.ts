import { http } from "@/utils/http";
import type { ShopInfoUpdateFormDTO, ShopInfoVO } from "@/views/shop/utils/types";

/** 获取当前店铺信息表单数据 */
export const getShopInfo = () => {
  return http.request<ShopInfoVO>("get", "/shop/getShopInfoVO");
};

/** 更新店铺信息 */
export const updateShopInfo = (data: ShopInfoUpdateFormDTO) => {
  return http.request<boolean>("put", "/shop/updateShopInfo", { data });
};
