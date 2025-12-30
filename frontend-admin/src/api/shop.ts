import { http } from "@/utils/http";
import { Result } from "@/utils/http/types";

export type ShopInfoUpdateFormDTO = {
  logoFileId: number;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

export type ShopInfoVO = {
  logoFileId: number;
  logoFileUrl: string;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

/** 获取当前店铺信息表单数据 */
export const getShopInfo = () => {
  return http.request<Result<ShopInfoVO>>("get", "/admin/shop");
};

/** 更新店铺信息 */
export const updateShopInfo = (data: ShopInfoUpdateFormDTO) => {
  return http.request<Result<boolean>>("put", "/admin/shop", { data });
};
