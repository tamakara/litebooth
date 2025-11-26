import http from "@/api/http.js";
import {AxiosResponse} from "axios";

export function fetchHomeInfoVO(): Promise<AxiosResponse<HomeInfoVO>> {
    return http.get('/home/vo/HomeInfoVO')
}
