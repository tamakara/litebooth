import {AxiosResponse} from "axios";
import http from "@/api/http";


export function fetchCaptchaInfoVO(): Promise<AxiosResponse<CaptchaInfoVO>> {
    return http.get('/captcha/vo/CaptchaInfoVO')
}
