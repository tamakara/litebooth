import http from "./http.js";
import {AxiosResponse} from "axios";

export function fetchItemCardPageVO(form: ItemCardPageQueryFormDTO): Promise<AxiosResponse<ItemCardPageVO>> {
    return http.get('/item/vo/ItemCardPageVO', {
        params: form
    })
}

export function fetchItemInfoVO(itemId: string): Promise<AxiosResponse<ItemInfoVO>> {
    return http.get(`/item/vo/ItemInfoVO/${itemId}`)
}

