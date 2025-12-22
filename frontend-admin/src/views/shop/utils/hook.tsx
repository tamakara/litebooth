import {ref, onMounted} from "vue";
import {message} from "@/utils/message";
import {updateShopInfo, getShopInfo} from "@/api/shop";
import type {ShopInfoVO, ShopInfoUpdateFormDTO,} from "./types";
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
    const res = await uploadFile(file);
    form.value.logoFileId = res;
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
      const data = await getShopInfo();
      const vo = data as ShopInfoVO;
      form.value = {
        logoFileId: vo.logoFileId ||0,
        logoTitle: vo.logoTitle || "",
        homeTitle: vo.homeTitle || "",
        homeSubtitle: vo.homeSubtitle || "",
        homeAnnouncement: vo.homeAnnouncement || ""
      };
      logoPreviewUrl.value = vo.logoFileUrl;
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
        await updateShopInfo(form.value);
        message("保存成功", {type: "success"});
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

  onMounted(() => {
    loadShopInfo();
  });

  return {
    form,
    formRef,
    loading,
    logoPreviewUrl,
    customUploadRequest,
    handleSubmit,
    handleReset
  };
}
