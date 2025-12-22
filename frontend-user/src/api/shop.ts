import http from "@/api/http.js";
import {AxiosResponse} from "axios";
import { ShopInfoVO} from "@/types";

export function fetchShopInfoVO(): Promise<AxiosResponse<ShopInfoVO>> {
    return http.get('/shop/getShopInfoVO')
}
