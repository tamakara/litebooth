import http from "./http.js";
import {AxiosResponse} from "axios";

export function fetchItemCardListPageVO(form: ItemCardQueryFormDTO): Promise<AxiosResponse<ItemCardListPageVO>> {
    return http.get('/item/vo/ItemCardListPageVO', {
        params: form
    })
}

export function fetchItemInfoVO(itemId: string): Promise<AxiosResponse<ItemInfoVO>> {
    return http.get(`/item/vo/ItemInfoVO/${itemId}`)
}

