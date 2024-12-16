import request from '@/utils/request'

export function all(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/package/list',
        method: 'get',
        params
    })
}
