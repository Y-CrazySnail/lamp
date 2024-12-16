import request from '@/utils/request'

export function dict(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-category/dict',
        method: 'get',
        params
    })
}