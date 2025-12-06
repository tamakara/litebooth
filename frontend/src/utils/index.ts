import dayjs from 'dayjs'

export function formatDate(date: string | null, formatStr: string = 'YYYY年MM月DD日 HH:mm:ss') {
    if (!date) return ''
    return dayjs(date).format(formatStr)
}
