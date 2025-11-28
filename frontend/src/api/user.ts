import http from "@/api/http.js";
import {passwordEncrypt} from "@/utils";
import {AxiosResponse} from "axios";

export function login(form: LoginFormDTO) {
    form.password = passwordEncrypt(form.password)
    return http.post('/user/login', form)
}

export function register(form: RegisterFormDTO) {
    form.password = passwordEncrypt(form.password)
    return http.post('/user/register', form)
}

export function fetchProfileVO(): Promise<AxiosResponse<ProfileVO>> {
    return http.get('/user/vo/ProfileVO')
}

export function updateAvatar(file) {
    const formData = new FormData();
    formData.append('file', file);
    return http.put('/user/updateAvatar', formData)
}

export function sendEmailCode(form) {
    return http.put('/user/updateEmail', form)
}


export function updateEmail(form) {
    return http.put('/user/updateEmail', form)
}

export function updatePassword(form) {
    return http.put('/user/updatePassword', form)
}

