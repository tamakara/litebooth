import { http } from "@/utils/http";
import { Result } from "@/utils/http/types";

/** 上传文件，返回文件ID字符串 */
export const uploadFile = (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  return http.request<Result<number>>("post", "/admin/files", {
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};



