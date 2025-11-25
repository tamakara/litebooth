import axios from 'axios'
import {apiConfig} from "@/config";

const http = axios.create(apiConfig)

http.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')
    if (token) config.headers.Authorization = `Bearer ${token}`
    return config
})

http.interceptors.response.use(
    (res) => res,
    (err) => {
        if (err.response?.status === 401) {
            localStorage.removeItem('token')
            window.location.reload()
        }
        return Promise.reject(err)
    }
)

export default http
