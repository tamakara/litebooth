import {AxiosResponse} from "axios";
import http from "@/api/http";
import {CaptchaInfoVO} from "@/types/captcha";

export function fetchCaptchaInfoVO(): Promise<AxiosResponse<CaptchaInfoVO>> {
    return http.get('/captcha/vo/CaptchaInfoVO')
}
