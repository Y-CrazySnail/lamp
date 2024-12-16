import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/order/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/order/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/order/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/order/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/order/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/order/remove',
        method: 'delete',
        data: params
    })
}