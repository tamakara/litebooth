<script setup lang="ts">
import {ElButton, ElForm, ElFormItem, ElImage, ElInput, ElMessage, ElUpload} from "element-plus";
import {onMounted, ref} from "vue";

import {
  uploadShopLogo,
  type ShopInfoUpdateFormDTO,
  updateShopInfo,
  getShopInfoUpdateForm,
  type ShopInfoUpdateFormVO
} from "@/api/shop";

const formRef = ref<InstanceType<typeof ElForm> | null>(null);

defineOptions({
  name: "Shop"
});

const form = ref<ShopInfoUpdateFormDTO>({
  logo: "",
  logoTitle: "",
  homeTitle: "",
  homeSubtitle: "",
  homeAnnouncement: ""
});

const rules = {
  logo: [{required: true, message: "请上传店铺 Logo", trigger: "change"}],
  logoTitle: [{required: true, message: "请输入 Logo 标题", trigger: "blur"}],
  homeTitle: [{required: true, message: "请输入首页标题", trigger: "blur"}],
  homeSubtitle: [{required: true, message: "请输入首页副标题", trigger: "blur"}],
  homeAnnouncement: [{required: true, message: "请输入首页公告", trigger: "blur"}]
};

// 本地选择的 Logo 预览 URL（使用本地文件预览，不走服务器）
const logoPreviewUrl = ref<string>("");

// 上传 Logo 文件，只负责调用接口并保存返回的 id，不再使用返回的图片地址做预览
const handleLogoUpload = async (file: File) => {
  const res = await uploadShopLogo(file);
  const fileId = res as unknown as string;
  form.value.logo = fileId;
};

// 自定义上传请求，顺便生成本地预览
const customUploadRequest = (options: any) => {
  const {file, onSuccess, onError} = options;
  // 使用本地文件生成预览地址
  const rawFile = file as File;
  if (rawFile) {
    logoPreviewUrl.value = URL.createObjectURL(rawFile);
  }
  return handleLogoUpload(rawFile)
    .then(() => {
      ElMessage.success("上传成功");
      onSuccess && onSuccess({}, options);
    })
    .catch((err: any) => {
      console.error(err);
      ElMessage.error("上传失败");
      onError && onError(err);
    });
};

// 页面加载时获取当前店铺信息并回显到表单
const loading = ref(false);

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
    logoPreviewUrl.value = vo.logoUrl
  } catch (e) {
    console.error(e);
    ElMessage.error("加载店铺信息失败");
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadShopInfo();
});

// 提交表单
const handleSubmit = () => {
  formRef.value?.validate(async valid => {
    if (!valid) return;
    try {
      await updateShopInfo(form.value);
      ElMessage.success("保存成功");
    } catch (e) {
      console.error(e);
      ElMessage.error("保存失败");
    }
  });
};

// 重置表单
const handleReset = () => {
  formRef.value?.resetFields();
  logoPreviewUrl.value = "";
};

</script>

<template>
  <el-card shadow="never" :body-style="{ height: 'calc(100vh - 260px)' }">
    <template #header>
      店铺管理
    </template>
    <div class="max-w-[640px] m-auto p-4">
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        label-position="right"
      >
        <!-- Logo 头像上传 -->
        <el-form-item label="店铺 Logo" prop="logo">
          <el-upload
            class="avatar-uploader"
            :action="''"
            :show-file-list="false"
            :limit="1"
            :http-request="customUploadRequest"
            accept="image/*"
          >
            <el-image
              :src="logoPreviewUrl "
              style="width: 80px; height: 80px; border-radius: 50%; border: 1px dashed #d9d9d9; cursor: pointer; object-fit: cover;"
              fit="cover"
            />
          </el-upload>
          <span class="text-xs text-gray-500 leading-snug ml-3">
          点击头像更换 Logo，建议尺寸 200×200，支持 JPG/PNG
        </span>
        </el-form-item>

        <el-form-item label="Logo 标题" prop="logoTitle">
          <el-input v-model="form.logoTitle" placeholder="请输入 Logo 标题"/>
        </el-form-item>

        <el-form-item label="首页标题" prop="homeTitle">
          <el-input v-model="form.homeTitle" placeholder="请输入首页标题"/>
        </el-form-item>

        <el-form-item label="首页副标题" prop="homeSubtitle">
          <el-input v-model="form.homeSubtitle" placeholder="请输入首页副标题"/>
        </el-form-item>

        <el-form-item label="首页公告" prop="homeAnnouncement">
          <el-input
            v-model="form.homeAnnouncement"
            type="textarea"
            :rows="3"
            :autosize="{ minRows: 3, maxRows: 6 }"
            placeholder="请输入首页公告内容，支持换行"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<style scoped>
</style>
