import { ref, onMounted } from "vue";
import { message } from "@/utils/message";
import {
  uploadShopLogo,
  updateShopInfo,
  getShopInfoUpdateForm
} from "@/api/shop";
import type {
  ShopInfoUpdateFormVO,
  ShopInfoUpdateFormDTO
} from "./types";

export function useShop() {
  const formRef = ref();
  const loading = ref(false);
  const logoPreviewUrl = ref<string>("");

  const form = ref<ShopInfoUpdateFormDTO>({
    logo: "",
    logoTitle: "",
    homeTitle: "",
    homeSubtitle: "",
    homeAnnouncement: ""
  });

  const handleLogoUpload = async (file: File) => {
    const res = await uploadShopLogo(file);
    form.value.logo = res as unknown as string;
  };

  const customUploadRequest = (options: any) => {
    const { file, onSuccess, onError } = options;
    const rawFile = file as File;
    if (rawFile) {
      logoPreviewUrl.value = URL.createObjectURL(rawFile);
    }
    return handleLogoUpload(rawFile)
      .then(() => {
        message("上传成功", { type: "success" });
        onSuccess && onSuccess({}, options);
      })
      .catch((err: any) => {
        console.error(err);
        message("上传失败", { type: "error" });
        onError && onError(err);
      });
  };

  const loadShopInfo = async () => {
    loading.value = true;
    try {
      const data = await getShopInfoUpdateForm();
      const vo = data as unknown as ShopInfoUpdateFormVO;
      form.value = {
        logo: vo.logo || "",
        logoTitle: vo.logoTitle || "",
        homeTitle: vo.homeTitle || "",
        homeSubtitle: vo.homeSubtitle || "",
        homeAnnouncement: vo.homeAnnouncement || ""
      };
      logoPreviewUrl.value = vo.logoUrl;
    } catch (e) {
      console.error(e);
      message("加载店铺信息失败", { type: "error" });
    } finally {
      loading.value = false;
    }
  };

  const handleSubmit = () => {
    formRef.value?.validate(async valid => {
      if (!valid) return;
      try {
        await updateShopInfo(form.value);
        message("保存成功", { type: "success" });
      } catch (e) {
        console.error(e);
        message("保存失败", { type: "error" });
      }
    });
  };

  const handleReset = () => {
    formRef.value?.resetFields();
    logoPreviewUrl.value = "";
    loadShopInfo(); // Reset should probably reload original data or clear? Usually reset to initial state.
    // If we want to clear everything:
    // form.value = { ... };
    // But usually reset means reset to what was loaded.
    // Element Plus resetFields resets to initial value of the model when form was mounted/initialized.
    // Since we load data async, we might need to be careful.
    // But let's stick to simple resetFields for now, or reload.
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
