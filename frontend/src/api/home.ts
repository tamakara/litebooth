import http from "@/api/http.js";
import {AxiosResponse} from "axios";
import {HomeInfoVO} from "@/types";

export function fetchHomeInfoVO(): Promise<AxiosResponse<HomeInfoVO>> {
    return http.get('/home/vo/HomeInfoVO')
}
