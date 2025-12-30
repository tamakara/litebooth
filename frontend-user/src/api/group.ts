import {GroupVO} from "@/types";
import http from "@/api/http";

export function fetchGroupListVO(): Promise<GroupVO[]> {
    return http.get('/user/groups')
}
