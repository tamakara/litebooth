import {http} from "@/utils/http";
import {Result} from "@/utils/http/types";

export type LoginData = {
  success: boolean;
  data: {
    /** 头像 */
    avatar: string;
    /** 用户名 */
    username: string;
    /** 昵称 */
    nickname: string;
    /** 当前登录用户的角色 */
    roles?: Array<string>;
    /** 按钮级别权限 */
    permissions?: Array<string>;
    /** `token` */
    accessToken: string;
    /** 用于调用刷新`accessToken`的接口时所需的`token` */
    refreshToken: string;
    /** `accessToken`的过期时间（格式'xxxx/xx/xx xx:xx:xx'） */
    expires: Date;
  };
};

export type UserResult = Result<LoginData>;

export type RefreshTokenResult = Result<{
  /** `token` */
  accessToken: string;
  /** 用于调用刷新`accessToken`的接口时所需的`token` */
  refreshToken: string;
  /** `accessToken`的过期时间（格式'xxxx/xx/xx xx:xx:xx'） */
  expires: Date;
}>;

type AdminInfoVO = UserResult

/** 登录 */
export const getLogin = (data?: object) => {
  return http.request<AdminInfoVO>("post", "/admin/auth/login", {data});
};

type RefreshTokenVO = RefreshTokenResult

/** 刷新`token` */
export const refreshTokenApi = (data?: object) => {
  return http.request<RefreshTokenVO>("post", "/admin/auth/refresh-token", {data});
};

export type ChangePasswordDTO = {
  oldPassword?: string;
  newPassword?: string;
};

export const changePassword = (data: ChangePasswordDTO) => {
  return http.request<Result<void>>("put", "/admin/auth/password", {data});
};
