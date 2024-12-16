import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'auth',
        url: '/permission/page',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'auth',
        url: '/permission/all',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'auth',
        url: '/permission/baseSave',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'auth',
        url: '/permission/baseUpdate',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'auth',
        url: '/permission/baseDelete',
        method: 'delete',
        data: params
    })
}