import http from "@/api/http.js";
import {AxiosResponse} from "axios";

export function fetchHomeInfoVO(): Promise<AxiosResponse<HomeVO>> {
    return http.get('/home/fetchHomeInfoVO')
}
