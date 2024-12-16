import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-order/page',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-order/getById',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-order/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-order/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-order/remove',
        method: 'delete',
        data: params
    })
}

export function refund(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-order/refund',
        method: 'post',
        data: params
    })
}