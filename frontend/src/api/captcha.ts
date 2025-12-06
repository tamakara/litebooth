import {AxiosResponse} from "axios";
import http from "@/api/http";
import {CaptchaDTO, CaptchaInfoVO} from "@/types/captcha";

export function fetchCaptchaInfoVO(): Promise<AxiosResponse<CaptchaInfoVO>> {
    return http.get('/captcha/vo/CaptchaInfoVO')
}

export function verifyCaptchaCode(captchaDTO: CaptchaDTO): Promise<AxiosResponse<string>> {
    return http.post('/captcha/verify', captchaDTO)
}
