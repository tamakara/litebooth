import http from "@/api/http";
import { ShopInfoVO} from "@/types";

export function fetchShopInfoVO(): Promise<ShopInfoVO> {
    return http.get('/user/shop')
}
