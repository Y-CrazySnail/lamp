import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/store/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/store/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/store/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/store/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/store/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/store/remove',
        method: 'delete',
        data: params
    })
}