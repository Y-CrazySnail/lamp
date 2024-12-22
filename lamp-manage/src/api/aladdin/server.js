import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/page',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/get',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/list',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/remove',
        method: 'delete',
        data: params
    })
}

export function sync(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-server/sync',
        method: 'post',
        data: params
    })
}
