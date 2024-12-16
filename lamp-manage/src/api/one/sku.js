import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/sku/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/sku/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/sku/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/sku/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/sku/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/sku/remove',
        method: 'delete',
        data: params
    })
}