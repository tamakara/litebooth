import http from "./http.js";

export function getItemCardListVO(searchForm) {
    return http.get('/item/getItemCardListVO', {
        params: searchForm
    })
}

export function getItemDetail(id) {
    return http.get(`/item/getItemDetail/${id}`)
}
