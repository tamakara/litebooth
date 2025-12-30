import http from "./http";
import {ItemCardPageQueryFormDTO, ItemCardPageVO, ItemInfoVO} from "@/types";

export function fetchItemCardPageVO(form: ItemCardPageQueryFormDTO): Promise<ItemCardPageVO> {
    return http.get('/user/items', {
        params: form
    })
}

export function fetchItemInfoVO(itemId: string): Promise<ItemInfoVO> {
    return http.get(`/user/items/${itemId}`)
}

