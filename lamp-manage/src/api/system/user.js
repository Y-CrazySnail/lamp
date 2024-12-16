import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'auth',
        url: '/user/page',
        method: 'get',
        params
    })
}

export function roleIdList(params) {
    return request({
        baseURL: 'auth',
        url: '/user/getRoleIdList',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'auth',
        url: '/user/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'auth',
        url: '/user/baseUpdate',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'auth',
        url: '/user/baseDelete',
        method: 'delete',
        data: params
    })
}