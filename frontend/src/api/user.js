import http from "@/api/http.js";
import passwordEncrypt from "@/utils/passwordEncrypt.js";

export function login(form) {
    form.password = passwordEncrypt(form.password)
    return http.post('/user/login', form)
}


export function register(form) {
    form.password = passwordEncrypt(form.password)
    return http.post('/user/register', form)
}