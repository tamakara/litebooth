import {http} from "@/utils/http";

export type UserResult = {
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

export type RefreshTokenResult = {
  success: boolean;
  data: {
    /** `token` */
    accessToken: string;
    /** 用于调用刷新`accessToken`的接口时所需的`token` */
    refreshToken: string;
    /** `accessToken`的过期时间（格式'xxxx/xx/xx xx:xx:xx'） */
    expires: Date;
  };
};

type AdminInfoVO = UserResult

/** 登录 */
export const getLogin = (data?: object) => {
  return http.request<AdminInfoVO>("post", "/auth/login", {data});
};

type RefreshTokenVO = RefreshTokenResult

/** 刷新`token` */
export const refreshTokenApi = (data?: object) => {
  return http.request<RefreshTokenVO>("post", "/auth/refresh-token", {data});
};

export type ChangePasswordDTO = {
  oldPassword?: string;
  newPassword?: string;
};

export const changePassword = (data: ChangePasswordDTO) => {
  return http.request<void>("put", "/auth/changePassword", {data});
};
