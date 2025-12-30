import axios, {AxiosInstance, AxiosResponse} from 'axios'
import {apiConfig} from "@/config";
import {ElMessage} from "element-plus";
import {Result} from "@/types";

const http: AxiosInstance = axios.create(apiConfig)

http.interceptors.response.use(
    (response: AxiosResponse<Result>) => {
        const res = response.data;
        if (res.code === 200) {
            return res.data as any;
        } else {
            ElMessage.error(res.msg || 'Error');
            return Promise.reject(new Error(res.msg || 'Error'));
        }
    },
    (error) => {
        ElMessage.error(error.message || 'Network Error');
        return Promise.reject(error);
    }
)

export default http
