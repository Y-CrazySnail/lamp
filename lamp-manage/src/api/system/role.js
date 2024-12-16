import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'auth',
        url: '/role/page',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'auth',
        url: '/role/all',
        method: 'get',
        params
    })
}

export function permissionIdList(params) {
    return request({
        baseURL: 'auth',
        url: '/role/getPermissionIdList',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'auth',
        url: '/role/baseSave',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'auth',
        url: '/role/baseUpdate',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'auth',
        url: '/role/baseDelete',
        method: 'delete',
        data: params
    })
}