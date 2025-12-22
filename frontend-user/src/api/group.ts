import {AxiosResponse} from "axios";
import {GroupVO} from "@/types";
import http from "@/api/http";

export function fetchGroupListVO(): Promise<AxiosResponse<GroupVO[]>> {
    return http.get('/group/getGroupListVO')
}
