import request from '@/utils/request'

export function all(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/lamp-product/list',
        method: 'get',
        params
    })
}
