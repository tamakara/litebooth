import { http } from "@/utils/http";

export type ShopInfoUpdateFormDTO = {
  logo: string;
  logoTitle: string;
  homeTitle: string;
  homeSubtitle: string;
  homeAnnouncement: string;
};

/** 上传店铺Logo，返回文件ID字符串 */
export const uploadShopLogo = (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  return http.request<string>("post", "/uploadFile", {
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};

/** 更新店铺信息 */
export const updateShopInfo = (data: ShopInfoUpdateFormDTO) => {
  return http.request<boolean>("post", "/shop/update", { data });
};

