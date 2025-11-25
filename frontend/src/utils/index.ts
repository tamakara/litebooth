import dayjs from 'dayjs'
import SHA256 from "crypto-js/sha256.js";
import Base64 from "crypto-js/enc-base64.js";

export function formatDate(date: string, formatStr: string = 'YYYY年MM月DD日 HH:mm:ss') {
    if (!date) return ''
    return dayjs(date).format(formatStr)
}

export function passwordEncrypt(password: string) {
    if (!password) return ''
    return SHA256(password).toString(Base64)
}