import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-product/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-product/get',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-product/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-product/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-product/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-product/remove',
        method: 'delete',
        data: params
    })
}