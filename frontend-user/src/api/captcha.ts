import http from "@/api/http";
import {CaptchaDTO, CaptchaInfoVO} from "@/types/captcha";

export function fetchCaptchaInfoVO(): Promise<CaptchaInfoVO> {
    return http.get('/user/captcha')
}

export function verifyCaptchaCode(captchaDTO: CaptchaDTO): Promise<string> {
    return http.post('/user/captcha/verify', captchaDTO)
}
