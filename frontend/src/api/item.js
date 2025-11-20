import http from "./http.js";

export function getItemCardListVO(searchForm) {
    return http.get('/item/getItemCardListVO', {
        params: searchForm
    })
}

export function getItemInfoVO(itemId) {
    return http.get(`/item/getItemInfoVO/${itemId}`)
}
