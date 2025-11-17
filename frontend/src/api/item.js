import http from "./http.js";

export function getItemCardListVO(searchForm) {
    return http.get('/item/getItemCardListVO', {
        params: searchForm
    })
}
