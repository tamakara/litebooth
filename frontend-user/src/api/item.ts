import http from "./http.js";
import {AxiosResponse} from "axios";
import {GroupListVO, ItemCardPageQueryFormDTO, ItemCardPageVO, ItemInfoVO} from "@/types";

export function fetchGroupListVO(): Promise<AxiosResponse<GroupListVO>> {
    return http.get('/item/vo/GroupListVO')
}

export function fetchItemCardPageVO(form: ItemCardPageQueryFormDTO): Promise<AxiosResponse<ItemCardPageVO>> {
    return http.get('/item/vo/ItemCardPageVO', {
        params: form
    })
}

export function fetchItemInfoVO(itemId: string): Promise<AxiosResponse<ItemInfoVO>> {
    return http.get(`/item/vo/ItemInfoVO/${itemId}`)
}

