import http from "@/api/http.js";

export function getHomeInfoVO() {
    return http.get('/home/getHomeInfoVO')
}