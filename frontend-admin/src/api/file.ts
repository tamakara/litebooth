import { http } from "@/utils/http";

/** 上传文件，返回文件ID字符串 */
export const uploadFile = (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  return http.request<number>("post", "/file/uploadFile", {
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};

