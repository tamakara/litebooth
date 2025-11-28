import axios from 'axios'
import {apiConfig} from "@/config";

const http = axios.create(apiConfig)

http.interceptors.response.use(
    (res) => res,
    (err) => {
        return Promise.reject(err)
    }
)

export default http
