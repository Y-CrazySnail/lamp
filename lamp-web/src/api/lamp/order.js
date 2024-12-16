import request from '@/utils/request'

export function all(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/order/list',
        method: 'get',
        params
    })
}

export function place(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/order/place',
        method: 'post',
        data: params
    })
}

export function pay(params) {
    return request({
        baseURL: 'lamp',
        url: '/web/order/pay',
        method: 'post',
        data: params
    })
}