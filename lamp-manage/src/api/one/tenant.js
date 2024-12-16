import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/tenant/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/tenant/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/tenant/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/tenant/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/tenant/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/tenant/remove',
        method: 'delete',
        data: params
    })
}