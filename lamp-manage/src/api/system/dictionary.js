import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'auth',
        url: '/dictionary/page',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'auth',
        url: '/dictionary/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'auth',
        url: '/dictionary/baseSave',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'auth',
        url: '/dictionary/baseUpdate',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'auth',
        url: '/dictionary/baseDelete',
        method: 'delete',
        data: params
    })
}