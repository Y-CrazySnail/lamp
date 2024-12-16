import request from '@/utils/request'

export function all(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/service/list',
        method: 'get',
        params
    })
}

export function apple(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/service/apple',
        method: 'get',
        params
    })
}

export function updateUUID(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/service/updateUUID',
        method: 'put',
        data: params
    })
}