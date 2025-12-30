import {ref} from "vue";
import {message} from "@/utils/message";
import {updateShopInfo, getShopInfo, type ShopInfoVO, type ShopInfoUpdateFormDTO} from "@/api/shop";
import {uploadFile} from "@/api/file";

export function useShop() {
  const formRef = ref();
  const loading = ref(false);
  const logoPreviewUrl = ref<string>("");

  const form = ref<ShopInfoUpdateFormDTO>({
    logoFileId: 0,
    logoTitle: "",
    homeTitle: "",
    homeSubtitle: "",
    homeAnnouncement: ""
  });

  const handleLogoUpload = async (file: File) => {
    const res: any = await uploadFile(file);
    if (res.code === 200) {
      form.value.logoFileId = res.data;
    } else {
      throw new Error(res.msg || "上传失败");
    }
  };

  const customUploadRequest = (options: any) => {
    const {file, onSuccess, onError} = options;
    const rawFile = file as File;
    if (rawFile) {
      logoPreviewUrl.value = URL.createObjectURL(rawFile);
    }
    return handleLogoUpload(rawFile)
      .then(() => {
        message("上传成功", {type: "success"});
        onSuccess && onSuccess({}, options);
      })
      .catch((err: any) => {
        console.error(err);
        message("上传失败", {type: "error"});
        onError && onError(err);
      });
  };

  const loadShopInfo = async () => {
    loading.value = true;
    try {
      const res: any = await getShopInfo();
      if (res.code === 200) {
        const vo = res.data;
        form.value = {
          logoFileId: vo.logoFileId || 0,
          logoTitle: vo.logoTitle || "",
          homeTitle: vo.homeTitle || "",
          homeSubtitle: vo.homeSubtitle || "",
          homeAnnouncement: vo.homeAnnouncement || ""
        };
        logoPreviewUrl.value = vo.logoFileUrl;
      } else {
        message(res.msg || "加载店铺信息失败", {type: "error"});
      }
    } catch (e) {
      console.error(e);
      message("加载店铺信息失败", {type: "error"});
    } finally {
      loading.value = false;
    }
  };

  const handleSubmit = () => {
    formRef.value?.validate(async valid => {
      if (!valid) return;
      try {
        const res: any = await updateShopInfo(form.value);
        if (res.code === 200) {
          message("保存成功", {type: "success"});
        } else {
          message(res.msg || "保存失败", {type: "error"});
        }
      } catch (e) {
        console.error(e);
        message("保存失败", {type: "error"});
      }
    });
  };

  const handleReset = () => {
    formRef.value?.resetFields();
    logoPreviewUrl.value = "";
    loadShopInfo();
  };

  return {
    form,
    formRef,
    loading,
    logoPreviewUrl,
    customUploadRequest,
    handleSubmit,
    handleReset,
    loadShopInfo
  };
}
