import http from "@/api/http.js";
import {passwordEncrypt} from "@/utils";

export function login(form) {
    form.password = passwordEncrypt(form.password)
    return http.post('/user/login', form)
}

export function register(form) {
    form.password = passwordEncrypt(form.password)
    return http.post('/user/register', form)
}

export function getProfileVO() {
    return http.get('/user/getProfileVO')
}

export function getOrderListVO(form) {
    return http.get('/user/getOrderListVO', form)
}

export function updateAvatar(file) {
    const formData = new FormData();
    formData.append('file', file);
    return http.put('/user/updateAvatar', formData)
}

