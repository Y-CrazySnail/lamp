import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/server/pages',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/server/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/server/list',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/server/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/server/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/server/delete',
        method: 'delete',
        data: params
    })
}
